package spc_proj.main;

import java.sql.Connection;

import spc_proj.dao.UserDAO;
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
	UserDAO wrap = new UserDAO(conn);
	int workType = 0;
	
	public Proj_thread(Connection conn, String aToken, LogHandler log, int workType) {
		super();
		this.conn = conn;
		this.accessToken = aToken;
		this.logger = log;
		this.workType = workType;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//logger.info("Thread start!! accessToken["+accessToken+"] db["+conn.toString()+"]");
		
		logger.info("Thread start!! accessToken["+accessToken+"]");
		
		if (workType==1 || workType == 2){
			while (!killSw) {
				getPublic();
				
				for (int i = 0; i<statusArray.length;i++){
					getFriends(statusArray[i].getUser().getScreenName());
				}
				for (int i = 0; i<statusArray.length;i++){
					getFollowers(statusArray[i].getUser().getScreenName());
				}
			}
		} else if (workType ==3){
			friendArray = new User[10][];
			getFriends2("李开复", 0);
			
			followerArray = new User[10][];
			getFollowers2("李开复", 0);
		}
	}
	
	private void getFollowers2(String name, int depth) {
		// TODO Auto-generated method stub
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		String screen_name = name;
		Weibo_user wu = null;
		int i = 0;
		
		try {
			UserWapper users = fm.getFollowersByName(screen_name);
			int count = (int)users.getTotalNumber();
			followerArray[depth] = new User[count];
			for(User u : users.getUsers()){
				logger.info(u.toString());
				
				wu = new Weibo_user(u);
				wrap.insert(wu);
				
				followerArray[depth][i] = u;
				i++;
			}
			
			depth++;
			
			if (depth > 9) {
				followerArray[depth-1] = null;
				return;
			}
			
			for (i = 0; i< followerArray.length; i++) {
				getFollowers2(followerArray[depth][i].getScreenName(), depth);
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
			UserWapper users = fm.getFollowersByName(screen_name);
			for(User u : users.getUsers()){
				logger.info(u.toString());
				
				wu = new Weibo_user(u);
				wrap.insert(wu);
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
			int count = (int)users.getTotalNumber();
			friendArray[depth] = new User[count];
			for(User u : users.getUsers()){
				logger.info(u.toString());
				
				wu = new Weibo_user(u);
				wrap.insert(wu);
				
				friendArray[depth][i] = u;
				i++;
			}
			
			depth++;
			
			if (depth > 9) {
				friendArray[depth-1] = null;
				return;
			}
			
			for (i = 0; i< friendArray.length; i++) {
				getFriends2(friendArray[depth][i].getScreenName(), depth);
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
				wrap.insert(wu);
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
				wrap.insert(wu);
				
				statusArray[i] = s;
				i++;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
