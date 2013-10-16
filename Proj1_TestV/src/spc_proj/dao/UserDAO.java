package spc_proj.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.crypto.spec.DHGenParameterSpec;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.utils.WConfig;
import spc_proj.utils.WeiboTime;
import spc_proj.wrapper.WeiboUser;


public class UserDAO {
	private WeiboUser wu = null;
	private LogHandler logger = null;
	private DbHandler dh = null;
	private static int count = 0;
	private volatile static PrintWriter fileWriter = null;

	static{
			try {
				fileWriter = new PrintWriter(new BufferedWriter(new FileWriter("UserDescription.WDB", true)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public UserDAO(LogHandler log, DbHandler dh) {
		this.logger = log;
		this.dh = dh;
	}
	
	public boolean setCrawled(String screenName){
		return dh.update("update weibo_user set crawled=1 where screen_name='" + screenName + "'");
	}
	public boolean writeDescription(String screen_name, String description){
		fileWriter.println(screen_name + WConfig.seperator + description);
		if (++count % WConfig.saveRows == 0)
			fileWriter.flush();
		return true;
	}
	public boolean insert(WeiboUser wu) {
		writeDescription(wu.getScreen_name(), wu.getDescription());
		String sql = "";
		sql =  "INSERT INTO weibo_user (id,screen_name,name,province,city,location," +
				//"description," + 
				"url,profile_image_url,user_domain,gender,followers_count," +
				"friends_count,statuses_count,favourites_count,created_at,following,verified," +
				"verified_type,allow_all_act_msg,allow_all_comment,follow_me,avatar_large," +
				"online_status,bi_followers_count,remark,lang,verified_reason,weihao," +
				"status_id,insert_date,crawl_level,crawled,uid,blog_url) VALUES (";
		sql += "'"+wu.getId()+"',";
		sql += "'"+wu.getScreen_name()+"',";
		sql += "'"+wu.getName()+"',";
		sql += "'"+wu.getProvince()+"',";
		sql += "'"+wu.getCity()+"',";
		sql += "'"+wu.getLocation()+"',";
		//sql += "'"+wu.getDescription().replaceAll("'", "‘")+"',";
		sql += "'"+wu.getUrl()+"',";
		sql += "'"+wu.getProfile_image_url()+"',";
		sql += "'"+wu.getUser_domain()+"',";
		sql += "'"+wu.getGender()+"',";
		//big bug!!!!!
		sql += "'"+wu.getFollowers_count()+"',";
		sql += "'"+wu.getFriends_count()+"',";
		sql += "'"+wu.getStatuses_count()+"',";
		sql += "'"+wu.getFavourites_count()+"',";
		sql += "'"+wu.getCreated_at()+"',";
		sql += "'"+(wu.isFollowing()?1:0)+"',";
		sql += "'"+(wu.isVerified()?1:0)+"',";
		sql += "'"+wu.getVerified_type()+"',";
		sql += "'"+(wu.isAllow_all_act_msg()?1:0)+"',";
		sql += "'"+(wu.isAllow_all_comment()?1:0)+"',";
		sql += "'"+(wu.isFollow_me()?1:0)+"',";
		sql += "'"+wu.getAvatar_large()+"',";
		sql += "'"+wu.getOnline_status()+"',";
		sql += "'"+wu.getBi_followers_count()+"',";
		sql += "'"+wu.getRemark().replaceAll("'", "")+"',";
		sql += "'"+wu.getLang()+"',";
		sql += "'"+wu.getVerified_reason()+"',";
		sql += "'"+wu.getWeihao()+"',";
		sql += "'"+wu.getStatus_id()+"',";
		sql += "'"+wu.getInsert_date()+"',";
		sql += "'"+wu.getCrawl_level()+"',";
		sql += "'"+wu.getCrawled()+"',";
		sql += "'"+wu.getUid()+"',";
		sql += "'"+wu.getBlog_url()+"')";
		
		
		if (dh.insert(sql)){
			logger.debug("Insert successfully. id["+wu.getId()+"] screen_name["+wu.getScreen_name()+"]");
			return true;
		}else return false;

/*		try {
			conn.insert(sql)
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			logger.debug("Insert successfully. id["+wu.getId()+"] screen_name["+wu.getScreen_name()+"]");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			logger.error("Insert Data failed. id["+wu.getId()+"] screen_name["+wu.getScreen_name()+"]");
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
		}*/
	}
}
