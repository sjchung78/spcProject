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
import weibo4j.org.json.JSONObject;
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
import java.util.logging.Logger;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.BurstFilter;

import com.mysql.jdbc.Statement;

public class FollowersCrawler extends Thread {
	// SN is short for screenName
	private static LogHandler classLogger = new LogHandler("BFSCrawler_Class");
	private static int step = 200;
	private static String bigName = WeiboConfig.getValue("firstScreeName");
	private static Semaphore semaphore = new Semaphore(1);
	private static int friendsCount = 0;
	private static int followersCount = 0;
	private static int statusesCount = 0;
	private volatile static int base = 0;

	private Connection conn = null;
	private String accessToken = null;
	private LogHandler logger = null;
	private UserDAO uDAO = null;
	private CommentDAO comDAO = null;
	private ConnectionDAO conDAO = null;
	static{
		Users um = new Users();
		um.client.setToken(WeiboConfig.getValue("main_access_token"));
		try {
			um.showUserById("1197161814");
			User u = um.showUserByScreenName(bigName);
			followersCount = u.getFollowersCount();
			friendsCount = u.getFriendsCount();
			statusesCount = u.getStatusesCount();
		} catch (Exception e) {
			e.printStackTrace();
			classLogger.error("error in getFriends()\n" + e.toString());
		}
	}

	public FollowersCrawler(String accessToken, String loggerName) {
		super();
		DbHandler dh = new DbHandler();
		logger = new LogHandler(loggerName);
		this.accessToken = accessToken;
		uDAO = new UserDAO(logger, dh);
		comDAO = new CommentDAO(logger, dh);
		conDAO = new ConnectionDAO(logger, dh);
	}
	private int getCursor(){
		int cursor = 0;
		try{
			semaphore.acquire();
			cursor = base;
			base += step;
		}catch(InterruptedException e){
			e.printStackTrace();
			logger.debug("addBase got Interrupted!");
		}
		semaphore.release();
		return cursor;
	}
	private void getFriends() {
		// TODO Auto-generated method stub
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		WeiboUser WU = null;
		try {
			int cursor = getCursor();
			logger.debug("Friends cursor: " + cursor);
			logger.error(""+cursor);
			UserWapper users = fm.getFollowersByName(FollowersCrawler.bigName, 200, cursor);
			for (User u : users.getUsers()) {
				logger.debug(u.toString());
				WU = new WeiboUser(u);// WeiboUser
				WU.setCrawl_level(1);
				WU.setCrawled(0);
				uDAO.insert(WU);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getFriends()\n" + e.toString());
		}
	}

	public void run() {
		UserNotCraw SU = null;
		int level;
		while(base < followersCount){
			getFriends();
		}
	}

	public static void main(String[] args) throws Exception {
		String[] accessTokens = AccessToken.getAll();
		for (int i = 0; i < accessTokens.length; i++){
			new FollowersCrawler(accessTokens[i], "BFSCrawler"+i).start();
		}
	}
}
