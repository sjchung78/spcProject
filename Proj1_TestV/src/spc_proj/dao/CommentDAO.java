package spc_proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import spc_proj.handler.LogHandler;
import spc_proj.wrapper.Weibo_comment;
import spc_proj.wrapper.Weibo_user;

public class CommentDAO {

	private Weibo_comment wc = null;
	private LogHandler logger = null;
	private Connection conn = null;
	
	public CommentDAO(LogHandler log, Connection conn) {
		this.logger = log;
		this.conn = conn;
	}
	
	public boolean insert(Weibo_comment wc) {
		String sql = "";
		
		sql =  "INSERT INTO weibo_comment (id,created_at,text,comment_type,comment_id,insert_date) VALUES (";
		sql += "'"+wc.getId()+"',";
		sql += "'"+wc.getCreated_at()+"',";
		sql += "'"+wc.getText().replaceAll("'", "")+"',";
		sql += "'"+wc.getComment_type()+"',";
		sql += "'"+wc.getComment_id()+"',";
		sql += "'"+wc.getInsert_date()+"')";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			logger.debug("Insert successfully. id["+wc.getId()+"] text["+wc.getText()+"]");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("Insert Data failed. id["+wc.getId()+"] text["+wc.getText()+"]");
			logger.error(sql);
			return false;
		}
		
		return true;
		 
	}

}
