package spc_proj.main;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.dao.CommentDAO;
import spc_proj.dao.ConnectionDAO;
import spc_proj.dao.UserDAO;
import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.utils.AccessToken;
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

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.logging.log4j.core.filter.BurstFilter;

import com.mysql.jdbc.Statement;

public class BFSCrawler extends Thread {
	// SN is short for screenName
	private static int sleepTime = 500;
	private static HashSet<String> SNAll = new HashSet<String>();
	private static Vector<UserNotCraw> SNNotCrawled = new Vector<UserNotCraw>();
	private static boolean SNAllUsing = false;//make sure SNAll is exclusively used by different threads, avoiding duplication
	private static boolean SNNotCrawledUsing = false;//the same
	private static LogHandler classLogger = new LogHandler("BFSCrawler_Class");
	private Connection conn = null;
	private String accessToken = null;
	private LogHandler logger = null;
	private UserDAO uDAO = null;
	private CommentDAO comDAO = null;
	private ConnectionDAO conDAO = null;

	public BFSCrawler(String aToken, String loggerName) {
		super();
		DbHandler dh = new DbHandler();
		logger = new LogHandler(loggerName);
		accessToken = aToken;
		uDAO = new UserDAO(logger, dh);
		comDAO = new CommentDAO(logger, dh);
		conDAO = new ConnectionDAO(logger, dh);
	}
	public static void SNAllAdd(String SN){
		while(SNAllUsing){
			try{
				Thread.sleep(BFSCrawler.sleepTime);
			}catch(InterruptedException IE){
				IE.printStackTrace();
				classLogger.error("Tread is interrupted!");
			}
		}
		SNAllUsing = true;
		SNAll.add(SN);
		SNAllUsing = false;
	}
	public static void SNNotCrawledAdd(UserNotCraw u){
		while(SNNotCrawledUsing){
			try{
				Thread.sleep(BFSCrawler.sleepTime);
			}catch(InterruptedException IE){
				IE.printStackTrace();
				classLogger.error("Tread is interrupted!");
			}
		}
		SNNotCrawledUsing = true;
		SNNotCrawled.add(u);
		SNNotCrawledUsing = false;
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
			SNNotCrawledAdd(new UserNotCraw(SN, 0));
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			classLogger.error("Can't addFirstScreenName");
			e.printStackTrace();
		}

	}

	public static void loadAllUsers() {
		String sql = "select screen_name, crawled, crawl_level from weibo_user";
		DbHandler dh = new DbHandler();
		ResultSet rs = dh.query(sql);
		boolean hasSN = false;
		try {
			while (rs.next()) {
				hasSN = true;
				String SN = rs.getString(1);
				int crawled = rs.getInt(2);
				int crawlLevel = rs.getInt(3);
				BFSCrawler.SNAllAdd(SN);
				if (crawled == 0) {
					BFSCrawler.SNNotCrawledAdd(new UserNotCraw(SN, crawlLevel));
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

	private void getFriends(String name, int level) {
		// TODO Auto-generated method stub
		String screen_name = name;
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		WeiboUser WU = null;
		try {
			UserWapper users = fm.getFriendsByScreenName(screen_name);
			for (User u : users.getUsers()) {
				logger.debug(u.toString());
				String SN = u.getScreenName();
				if (!SNAll.contains(SN)) {
					WU = new WeiboUser(u);// WeiboUser
					WU.setCrawl_level(level + 1);
					WU.setCrawled(0);
					uDAO.insert(WU);
					SNAllAdd(SN);
					SNNotCrawledAdd(new UserNotCraw(SN, level + 1));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getFriends()\n" + e.toString());
		}
	}

	public void run() {
		UserNotCraw SU = null;
		int level;
		while (!SNNotCrawled.isEmpty()) {
			SU = SNNotCrawled.remove(0);
			String name = SU.getScreenName();
			SNNotCrawled.remove(SU);
			level = SU.getCrawlLevel();
			getFriends(name, level);
			uDAO.setCrawled(name);
		}
	}

	public static void main(String[] args) throws Exception {
		String[] accessTokens = AccessToken.getAll();
		BFSCrawler.loadAllUsers();
		for (int i = 0; i < accessTokens.length; i++){
			new BFSCrawler(accessTokens[i], "BFSCrawler"+i).start();
		}
	}
}
