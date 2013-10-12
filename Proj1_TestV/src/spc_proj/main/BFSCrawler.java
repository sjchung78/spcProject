package spc_proj.main;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.dao.CommentDAO;
import spc_proj.dao.ConnectionDAO;
import spc_proj.dao.UserDAO;
import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import weibo4j.Friendships;
import weibo4j.Users;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.mysql.jdbc.Statement;

public class BFSCrawler extends Thread{
	class User{
		String screenName;
		int crawled;
		int crawlLevel;
	}
	private static Vector<User> users = new Vector<User>();
	private Connection conn = null;
	private String accessToken = null;
	private LogHandler logger = new LogHandler("BFSCrawler");
	private UserDAO uDAO = null;
	private CommentDAO comDAO = null;
	private ConnectionDAO conDAO = null; 
	public BFSCrawler(String aToken, LogHandler log){
		super();
		DbHandler dh = new DbHandler();
		this.accessToken = aToken;
		this.logger = log;
		uDAO = new UserDAO(logger, dh);
		comDAO = new CommentDAO(logger, dh);
		conDAO = new ConnectionDAO(logger, dh);
	}
	
	public static void loadAll(){
		String sql = "select screen_name, crawled, crawl_level from weibo_user";
		DbHandler dh = new DbHandler();
		ResultSet rs = dh.query(sql);
		try {
			while(rs.next()){
				System.out.println(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			dh.close();
		}
	}
	public static void main(String[] args) throws Exception{
		BFSCrawler.loadAll();
	}
}
