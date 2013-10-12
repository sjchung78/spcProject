package spc_proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.wrapper.WeiboConnection;
import spc_proj.wrapper.WeiboUser;


public class ConnectionDAO {
	private WeiboConnection wc = null;
	private LogHandler logger = null;
	private DbHandler dh = null;
	
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
}
