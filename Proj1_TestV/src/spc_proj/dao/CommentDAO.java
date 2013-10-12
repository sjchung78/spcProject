package spc_proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.wrapper.WeiboComment;
import spc_proj.wrapper.WeiboUser;

public class CommentDAO {

	private WeiboComment wc = null;
	private LogHandler logger = null;
	private DbHandler dh = null;
	
	public CommentDAO(LogHandler log, DbHandler dh) {
		this.logger = log;
		this.dh = dh;
	}
	
	public boolean insert(WeiboComment wc) {
		String sql = "";
		
		sql =  "INSERT INTO weibo_comment (id,created_at,text,comment_type,comment_id,insert_date) VALUES (";
		sql += "'"+wc.getId()+"',";
		sql += "'"+wc.getCreated_at()+"',";
		sql += "'"+wc.getText().replaceAll("'", "")+"',";
		sql += "'"+wc.getComment_type()+"',";
		sql += "'"+wc.getComment_id()+"',";
		sql += "'"+wc.getInsert_date()+"')";
		if (dh.insert(sql)){
			logger.debug("Insert successfully. id["+wc.getId()+"] text["+wc.getText()+"]");
			return true;
		}else return false;
/*		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			logger.debug("Insert successfully. id["+wc.getId()+"] text["+wc.getText()+"]");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			logger.error("Insert Data failed. id["+wc.getId()+"] text["+wc.getText()+"]");
			logger.error(sql);
			try {
				if (conn.isClosed()) {
					logger.error("Reconnecting");
					DbHandler dh = new DbHandler();
					this.conn = dh.connect();
				}
			} catch (Exception ex) {
				
			}
			return false;
		}
		
		return true;*/
		 
	}

}
