package spc_proj.main;

import java.sql.Connection;

import spc_proj.dao.UserDAO;
import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.wrapper.Weibo_user;
import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
import weibo4j.model.UserWapper;

public class Proj_thread extends Thread {

	Connection conn = null;
	LogHandler logger = null;
	public boolean killSw = false;
	private String accessToken = null;
	StatusWapper sw = null;
	private Status[] statusArray = null;
	private User[][] friendArray = null;
	private User[][] followerArray = null;
	UserDAO wrap = null;
	int workType = 0;
	int totalNumber = 0;
	
	public Proj_thread(String aToken, LogHandler log, int workType) {
		super();
		DbHandler dh = new DbHandler();
		this.conn = dh.connect();
		this.accessToken = aToken;
		this.logger = log;
		this.workType = workType;
		wrap = new UserDAO(logger, conn);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//logger.info("Thread start!! accessToken["+accessToken+"] db["+conn.toString()+"]");
		
		logger.info("Thread start!! accessToken["+accessToken+"]");
		
		try {
			if (workType==1 || workType == 2){
				int endPoint = 0;
				while (!killSw) {
					getPublic();
					
					for (int i = 0; i<statusArray.length;i++){
						try {
							if (statusArray[i].getUser() == null || statusArray[i].getUser().getScreenName() == null || statusArray[i].getUser().getScreenName() == "") {
								logger.warn("data error id:["+statusArray[i].getUser().getId()+"] screen_name:["+statusArray[i].getUser().getScreenName()+"]");
							} else {
								getFriends(statusArray[i].getUser().getScreenName());
							}
						} catch (Exception ex) {
							logger.error("Unexpected Error. ex["+ex.toString()+"]");
						}
					}
					for (int i = 0; i<statusArray.length;i++){
						try {
							if (statusArray[i].getUser() == null || statusArray[i].getUser().getScreenName() == null || statusArray[i].getUser().getScreenName() == "") {
								logger.warn("data error id:["+statusArray[i].getUser().getId()+"] screen_name:["+statusArray[i].getUser().getScreenName()+"]");
							} else {
								getFollowers(statusArray[i].getUser().getScreenName());
							}
						} catch (Exception ex) {
							logger.error("Unexpected Error. ex["+ex.toString()+"]");
						}
					}
					if (endPoint > 500)
						killSw = true;
				}
			} else if (workType ==3){
				friendArray = new User[100][];
				getFriends2("李开复", 0);
				
				followerArray = new User[100][];
				getFollowers2("李开复", 0);
			}
		} catch (Exception e) {
			logger.error("Unexpected error.");
			e.printStackTrace();
		}
		logger.warn("Thread Terminated. killSw["+killSw+"] totalNumber["+totalNumber+"]");
	}
	
	private void getFollowers2(String name, int depth) {
		// TODO Auto-generated method stub
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		String screen_name = name;
		Weibo_user wu = null;
		int i = 0;
		
		try {
			UserWapper users = fm.getFollowersByName(screen_name, 200, 0);
			int count = (int)users.getTotalNumber();
			followerArray[depth] = new User[count];
			for(User u : users.getUsers()){
				logger.info(u.toString());
				
				wu = new Weibo_user(u);
				wu.setCrawl_level(depth);
				wu.setCrawled(workType);
				wrap.insert(wu);
				
				followerArray[depth][i] = u;
				i++;
				totalNumber++;
			}
			
			depth++;
			
			if (depth > 99) {
				return;
			}
			
			for (i = 0; i< followerArray[depth-1].length; i++) {
				getFollowers2(followerArray[depth-1][i].getScreenName(), depth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getFollowers(String name) {
		// TODO Auto-generated method stub
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		String screen_name = name;
		Weibo_user wu = null;
		
		try {
			UserWapper users = fm.getFollowersByName(screen_name, 200, 0);
			for(User u : users.getUsers()){
				logger.info(u.toString());
				
				wu = new Weibo_user(u);
				wu.setCrawl_level(0);
				wu.setCrawled(workType);
				wrap.insert(wu);
				
				totalNumber++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getFriends2(String name, int depth) {
		// TODO Auto-generated method stub
		String screen_name = name;
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		Weibo_user wu = null;
		int i = 0;
		
		try {
			UserWapper users = fm.getFriendsByScreenName(screen_name);
			int count = users.getUsers().size();
			friendArray[depth] = new User[count];
			for(User u : users.getUsers()){
				logger.info(u.toString());
				
				wu = new Weibo_user(u);
				wu.setCrawl_level(depth);
				wu.setCrawled(workType);
				wrap.insert(wu);
				
				friendArray[depth][i] = u;
				i++;
				totalNumber++;
			}
			
			depth++;
			
			if (depth > 99) {
				friendArray[depth-1] = null;
				return;
			}
			
			for (i = 0; i< friendArray[depth-1].length; i++) {
				getFriends2(friendArray[depth-1][i].getScreenName(), depth);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getFriends(String name) {
		// TODO Auto-generated method stub
		String screen_name = name;
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		Weibo_user wu = null;
		try {
			UserWapper users = fm.getFriendsByScreenName(screen_name);
			for(User u : users.getUsers()){
				logger.info(u.toString());
				
				wu = new Weibo_user(u);
				wu.setCrawl_level(0);
				wu.setCrawled(workType);
				wrap.insert(wu);
				
				totalNumber++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getPublic() {
		try {
			sw = null;
			
			Timeline tm = new Timeline();
			tm.client.setToken(accessToken);
			sw = tm.getPublicTimeline(200,0);
			Weibo_user wu = null;
			int count = (int)sw.getTotalNumber();
			statusArray = new Status[count];
			int i = 0;
			
			for(Status s : sw.getStatuses()){
				wu = new Weibo_user(s);
				wu.setCrawl_level(0);
				wu.setCrawled(workType);
				wrap.insert(wu);
				
				statusArray[i] = s;
				i++;
				
				totalNumber++;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
