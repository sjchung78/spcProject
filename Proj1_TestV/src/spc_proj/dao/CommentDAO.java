package spc_proj.dao;

import java.sql.Connection;

import spc_proj.wrapper.Weibo_comment;

public class CommentDAO {

	private Weibo_comment wc = null;
	private Connection conn = null;
	
	public CommentDAO(Connection con) {
		this.conn = con;
	}
	
	public void insert(Weibo_comment wc) {
		
		return;
		 
	}

}
