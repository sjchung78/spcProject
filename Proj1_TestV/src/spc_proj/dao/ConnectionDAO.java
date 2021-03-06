package spc_proj.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.utils.WConfig;
import spc_proj.utils.WeiboTime;
import spc_proj.wrapper.WeiboConnection;
import spc_proj.wrapper.WeiboUser;
import weibo4j.util.WeiboConfig;


public class ConnectionDAO {
	private WeiboConnection wc = null;
	private LogHandler logger = null;
	private DbHandler dh = null;
	private volatile static PrintWriter fileWriter = null;
	private volatile static int count = 0;
	static{
			try {
				fileWriter = new PrintWriter(new BufferedWriter(new FileWriter("weibo_connection.WDB", true)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

/*	
	  `relation` tinyint(4) DEFAULT '0' COMMENT,
	  0: b is friend of  b
	  1: b is follwer of a',
*/
	public ConnectionDAO(LogHandler log,  DbHandler dh) {
		this.logger = log;
		this.dh = dh;
	}
	
	public boolean insert(WeiboConnection wc) {
		String sql = "";
		
		sql =  "INSERT INTO weibo_connection (screen_name_a,screen_name_b, relation) VALUES (";
		sql += "'"+wc.getScreenNameA()+"',";
		sql += "'"+wc.getScreenNameB()+"',";
		sql += "'"+wc.getRelation()+"')";
		
		if (dh.insert(sql)){
			logger.debug("Insert successfully. screen_name_a["+wc.getScreenNameA()+"] screen_name_b["+wc.getScreenNameB()+"]");
			return true;
		}else{
			return false;
		}
	}
	public  boolean insert(String nameA, String nameB, int relation){
		fileWriter.println(nameA + WConfig.seperator + nameB + WConfig.seperator + relation + WConfig.seperator + WeiboTime.getTime());
		if (++count % WConfig.saveRows == 0)
			fileWriter.flush();
		return true;
	}
	public static void main(String args[]){
	}
}
