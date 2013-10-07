package spc_proj.dao;

import java.sql.Connection;

import spc_proj.wrapper.Weibo_user;


public class UserDAO {
	private Weibo_user wu = null;
	private Connection conn = null;
	
	public UserDAO(Connection con) {
		this.conn = con;
	}
	
	public void insert(Weibo_user wu) {
		
		return;
		 
	}
}
