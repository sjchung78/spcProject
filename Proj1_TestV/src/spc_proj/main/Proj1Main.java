package spc_proj.main;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import weibo4j.Friendships;
import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.Users;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

public class Proj1Main {
	
	/**
	 * @param args  
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		LogHandler logger = new LogHandler("Proj1Main");
//		String access_token = WeiboConfig.getValue("access_token");
////		String uid = "3643427605";
//		
//		String uid = "1694061470";
//		Users um = new Users();
//		um.client.setToken(access_token);
//		try {
//			User user = um.showUserById(uid);
//			logger.info(user.toString());
//		} catch (WeiboException e) {
//			e.printStackTrace();
//		}
		
//		String screenName = "Free_Wish";
//		Friendships fm = new Friendships();
//		fm.client.setToken(access_token);
//		try {
//			String[] ids = fm.getFriendsIdsByName(screenName);
//			for(String s : ids){
//				logger.info(s);
//			}
//		} catch (WeiboException e) {
//			e.printStackTrace();
//		}
		
//		String access_token = WeiboConfig.getValue("access_token");
//		Timeline tm = new Timeline();
//		tm.client.setToken(access_token);
//		try {
//			StatusWapper status = tm.getPublicTimeline();
//			logger.info(""+status.getTotalNumber());
//			for(Status s : status.getStatuses()){
//				logger.info(s.toString());
//			}
//			System.out.println(status.getNextCursor());
//			System.out.println(status.getPreviousCursor());
//			System.out.println(status.getTotalNumber());
//			logger.info(status.getHasvisible());
//		} catch (WeiboException e) {
//			e.printStackTrace();
//		}
		DbHandler dh = new DbHandler();
		dh.connect();
		dh.select();
	}

}
