package spc_proj.main;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.dao.CommentDAO;
import spc_proj.dao.ConnectionDAO;
import spc_proj.dao.UserDAO;
import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.utils.AccessToken;
import spc_proj.utils.WConfig;
import spc_proj.utils.Restriction;
import spc_proj.wrapper.WeiboUser;
import weibo4j.Friendships;
import weibo4j.Users;
import weibo4j.Weibo;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.logging.log4j.core.filter.BurstFilter;

import com.mysql.jdbc.Statement;

public class BFSCrawler extends Thread {
	// SN is short for screenName
	private static int sleepTime = 500;
	private volatile static HashSet<String> SNAll = new HashSet<String>();
	private volatile static Vector<UserNotCraw> SNNotCrawled = new Vector<UserNotCraw>();
	private static LogHandler classLogger = new LogHandler("BFSCrawler_Class");
	private Connection conn = null;
	private String accessToken = null;
	private LogHandler logger = null;
	private UserDAO uDAO = null;
	private CommentDAO comDAO = null;
	private ConnectionDAO conDAO = null;
	private final static int count = 200;
	
	
	private static Semaphore sem0 = new Semaphore(1);
	private static Semaphore sem1 = new Semaphore(1);
	private static Semaphore sem2 = new Semaphore(1);
	private static Semaphore sem3 = new Semaphore(1);
	
	private static void SNNotCrawledAdd(UserNotCraw u){
		try{
			sem0.acquire();
		SNNotCrawled.add(u);
			sem0.release();
		}catch(InterruptedException e){
			e.printStackTrace();
			classLogger.debug("addBase got Interrupted!");
		}
	}
	private static boolean SNNotCrawledIsEmpty(){
		boolean ret = false;
		try{
			sem1.acquire();
			if (SNNotCrawled.isEmpty()) ret = true;
			sem1.release();
		}catch(InterruptedException e){
			e.printStackTrace();
			classLogger.debug("addBase got Interrupted!");
		}
		return ret;
	}
	private static boolean SNAllContains(String name){
		boolean ret = false;
		try{
			sem2.acquire();
			if (SNAll.contains(name)) ret = true;
			sem2.release();
		}catch(InterruptedException e){
			e.printStackTrace();
			classLogger.debug("addBase got Interrupted!");
		}
		return ret;
	}
	private static void SNAllAdd(String name){
		try{
			sem3.acquire();
			SNAll.add(name);
			sem3.release();
		}catch(InterruptedException e){
			e.printStackTrace();
			classLogger.debug("addBase got Interrupted!");
		}
	}
	public BFSCrawler(String aToken, String loggerName) {
		super();
		DbHandler dh = new DbHandler();
		logger = new LogHandler(loggerName);
		accessToken = aToken;
		uDAO = new UserDAO(logger, dh);
		comDAO = new CommentDAO(logger, dh);
		conDAO = new ConnectionDAO(logger, dh);
	}
	public static void addFirstScreenName() {
		String firstSN = WeiboConfig.getValue("firstScreeName");
		Users UM = new Users();
		UM.client.setToken(AccessToken.getAll()[0]);
		try {
			User user = UM.showUserByScreenName(firstSN);
			WeiboUser WU = new WeiboUser(user);
			WU.setCrawl_level(0);
			WU.setCrawled(0);
			DbHandler dh = new DbHandler();
			UserDAO uDAO = new UserDAO(classLogger, dh);
			uDAO.insert(WU);
			String SN = WU.getScreen_name();
			SNAllAdd(SN);

			// new UserNotCraw(screenName, crawled, crawlLevel, statusesCount,
			// friendsCount, followersCount)
			SNNotCrawledAdd(new UserNotCraw(SN, 0, 0, WU.getStatuses_count(),
					WU.getFriends_count(), WU.getFollowers_count()));
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			classLogger.error("Can't addFirstScreenName");
			e.printStackTrace();
		}

	}

	public static void loadAllUsers() {
		// #"url,profile_image_url,user_domain,gender,followers_count," +
		// "friends_count,statuses_count,favourites_count,created_at,following,verified,"
		// +
		String sql = "select  screen_name, crawled, crawl_level, statuses_count,friends_count,followers_count   from weibo_user";
		DbHandler dh = new DbHandler();
		ResultSet rs = dh.query(sql);
		boolean hasSN = false;
		try {
			while (rs.next()) {
				hasSN = true;
				String screenName = rs.getString(1);
				int crawled = rs.getInt(2);
				int crawlLevel = rs.getInt(3);
				int statusesCount = rs.getInt(4);
				int friendsCount = rs.getInt(5);
				int followersCount = rs.getInt(6);
				BFSCrawler.SNAllAdd(screenName);
				if (crawled == 0) {
					// new UserNotCraw(screenName, crawled, crawlLevel,
					// statusesCount, friendsCount, followersCount)
					BFSCrawler.SNNotCrawledAdd(new UserNotCraw(screenName,
							crawled, crawlLevel, statusesCount, friendsCount,
							followersCount));
				}
			}
			if (!hasSN) {// If the set is empty, then add one
				addFirstScreenName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dh.close();
		}
	}

	private void addUsers(UserWapper users, UserNotCraw uPre) {
		WeiboUser WU = null;
		for (User u : users.getUsers()) {
			logger.debug(u.toString());
			String SN = u.getScreenName();
			conDAO.insert(uPre.getScreenName(), SN, 0);

			if (!SNAllContains(SN)) {
				WU = new WeiboUser(u);// WeiboUser
				WU.setCrawl_level(uPre.getCrawlLevel() + 1);
				WU.setCrawled(0);
				uDAO.insert(WU);
				SNAllAdd(SN);

				SNNotCrawledAdd(new UserNotCraw(SN, 0,
						uPre.getCrawlLevel() + 1, WU.getStatuses_count(), WU
								.getFriends_count(), WU.getFollowers_count()));
			}
		}
	}

	private void getFriendsAndFollowers(UserNotCraw uPre) {
		// TODO Auto-generated method stub
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		String screen_name = uPre.getScreenName();
		try {
			int cursor = 0;
			if (uPre.getFriendsCount() >= WConfig.minNumToCraw) {
				UserWapper users = fm.getFriendsByScreenName(screen_name);
				addUsers(users, uPre);
			}

			int followersCount = Math.min(Restriction.maxCrawFollowersCount,
					uPre.getFollowersCount());
			cursor = 0;
			while (followersCount - cursor >= WConfig.minNumToCraw) {
				UserWapper users = fm.getFollowersByName(screen_name, count,
						cursor);
				addUsers(users, uPre);
				cursor += count;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getFriends()\n" + e.toString());
		}
	}

	public void run() {
		UserNotCraw SU = null;
		int level;
		while (!SNNotCrawledIsEmpty()) {
			UserNotCraw u = SNNotCrawled.remove(0);
			getFriendsAndFollowers(u);
			uDAO.setCrawled(u.getScreenName());
		}
	}

	public static void main(String[] args) throws Exception {
		String[] accessTokens = AccessToken.getAll();
		BFSCrawler.loadAllUsers();
		for (int i = 0; i < accessTokens.length; i++) {
			new BFSCrawler(accessTokens[i], "BFSCrawler" + i).start();
		}
	}
}
