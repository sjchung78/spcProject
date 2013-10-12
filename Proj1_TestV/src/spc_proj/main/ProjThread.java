package spc_proj.main;

import java.lang.invoke.ConstantCallSite;
import java.sql.Connection;

import spc_proj.dao.CommentDAO;
import spc_proj.dao.UserDAO;
import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.wrapper.WeiboComment;
import spc_proj.wrapper.WeiboUser;
import weibo4j.Comments;
import weibo4j.Friendships;
import weibo4j.Timeline;
import weibo4j.model.Comment;
import weibo4j.model.CommentWapper;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;

public class ProjThread extends Thread {

	private Connection conn = null;
	private LogHandler logger = null;
	public boolean killSw = false;
	private String accessToken = null;
	private StatusWapper sw = null;
	private CommentWapper cw = null;
	private Status[] statusArray = null;
	private Comment[] commentArray = null;
	private User[][] friendArray = null;
	private User[][] followerArray = null;
	private UserDAO uDAO = null;
	private CommentDAO cDAO = null;
	public static int totalTypes = 3;
	int workType = 0;
	int totalNumber = 0;
	boolean isFriend = false;
	
	public ProjThread(String aToken, LogHandler log, int workType) {
		super();
		DbHandler dh = new DbHandler();
		this.accessToken = aToken;
		this.logger = log;
		this.workType = workType;
		uDAO = new UserDAO(logger, dh);
		cDAO = new CommentDAO(logger, dh);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//logger.info("Thread start!! accessToken["+accessToken+"] db["+conn.toString()+"]");
		
		logger.info("Thread start!! accessToken["+accessToken+"]");
		
		try {
			if (workType==0){
				try {
					int endPoint = 0;
					while (!killSw) {
						getPublic();
						int count = 0;
						if (statusArray==null){
							count = 0;
						} else {
							count = statusArray.length;
						}
						for (int i = 0; i<count;i++){
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
						for (int i = 0; i<count;i++){
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
				}catch (Exception ex) {
					logger.error("Error from workType 1.\n ex["+ex.toString()+"]");
				}
			} else if (workType ==1 ){
				while (!killSw) {
					try{
						getPublic();
						getComment();
					} catch (Exception ex) {
						logger.error("Error from workType 2.\n. ex["+ex.toString()+"]");
					}
				}
			} else if (workType ==2 ){
				try {
					friendArray = new User[100][];
					getFriends2("李开复", 0);
					
					followerArray = new User[100][];
					getFollowers2("李开复", 0);
					
				} catch (Exception ex) {
					ex.printStackTrace();
					logger.error("Error from workType 3.\n" + ex.toString());
				}
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
		WeiboUser wu = null;
		int i = 0;
		
		try {
			UserWapper users = fm.getFollowersByName(screen_name, 200, 0);
			int count = users.getUsers().size();
			followerArray[depth] = new User[count];
			for(User u : users.getUsers()){
				logger.debug(u.toString());
				
				wu = new WeiboUser(u);
				wu.setCrawl_level(depth);
				wu.setCrawled(workType);
				uDAO.insert(wu);
				
				followerArray[depth][i] = u;
				i++;
				totalNumber++;
			}
			
			isFriend = false;
			getCommentUser(followerArray[depth]);
			
			depth++;
			
			if (depth > 99) {
				return;
			}
						
			for (i = 0; i< followerArray[depth-1].length; i++) {
				getFollowers2(followerArray[depth-1][i].getScreenName(), depth);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getFollowers2()\n"+e.toString());
		}
	}
	
	private void getFollowers(String name) {
		// TODO Auto-generated method stub
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		String screen_name = name;
		WeiboUser wu = null;
		
		try {
			UserWapper users = fm.getFollowersByName(screen_name, 200, 0);
			for(User u : users.getUsers()){
				logger.debug(u.toString());
				
				wu = new WeiboUser(u);
				wu.setCrawl_level(0);
				wu.setCrawled(workType);
				uDAO.insert(wu);
				
				totalNumber++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getFollowers()\n"+e.toString());
		}
	}

	private void getFriends2(String name, int depth) {
		// TODO Auto-generated method stub
		String screen_name = name;
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		WeiboUser wu = null;
		int i = 0;
		
		try {
			UserWapper users = fm.getFriendsByScreenName(screen_name);
			int count = users.getUsers().size();
			friendArray[depth] = new User[count];
			for(User u : users.getUsers()){
				logger.debug(u.toString());
				
				wu = new WeiboUser(u);
				wu.setCrawl_level(depth);
				wu.setCrawled(workType);
				uDAO.insert(wu);
				
				friendArray[depth][i] = u;
				i++;
				totalNumber++;
			}
			
			isFriend = true;
			getCommentUser(friendArray[depth]);
			
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
			logger.error("error in getFriends2()\n"+e.toString());
		}
	}
	
	private void getFriends(String name) {
		// TODO Auto-generated method stub
		String screen_name = name;
		Friendships fm = new Friendships();
		fm.client.setToken(accessToken);
		WeiboUser wu = null;
		try {
			UserWapper users = fm.getFriendsByScreenName(screen_name);
			for(User u : users.getUsers()){
				logger.debug(u.toString());
				
				wu = new WeiboUser(u);
				wu.setCrawl_level(0);
				wu.setCrawled(workType);
				uDAO.insert(wu);
				
				totalNumber++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getFriends()\n"+e.toString());
		}
	}

	private void getPublic() {
		try {
			sw = null;
			
			Timeline tm = new Timeline();
			tm.client.setToken(accessToken);
			sw = tm.getPublicTimeline(200,0);
			WeiboUser wu = null;
			int count = sw.getStatuses().size();
			statusArray = new Status[count];
			int i = 0;
			
			for(Status s : sw.getStatuses()){
				wu = new WeiboUser(s);
				wu.setCrawl_level(0);
				wu.setCrawled(workType);
				uDAO.insert(wu);
				
				statusArray[i] = s;
				i++;
				
				totalNumber++;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error in getPublic()\n"+e.toString());
		}
	}
	
	private void getComment() {
		String id = "";
		Comments cm =new Comments();
		WeiboComment wc = null;
		cm.client.setToken(accessToken);
		try {
			for ( int i = 0 ; i < statusArray.length ; i++ ) {
				
				logger.debug(statusArray[i].toString());
				wc = new WeiboComment(statusArray[i]);
				cDAO.insert(wc);
				
				totalNumber++;
				
				id = statusArray[i].getId();
//				System.out.println(statusArray[i].toString());
				CommentWapper comment = cm.getCommentById(id);
				int count = comment.getComments().size();
				commentArray = new Comment[count];
				int j = 0;
				
				for(Comment c : comment.getComments()){
					logger.debug(c.toString());
					wc = new WeiboComment(c);
					cDAO.insert(wc);
					
					commentArray[j] = c;
					
					j++;
					totalNumber++;
				}
			}
		} catch (WeiboException e) {
			e.printStackTrace();
			logger.error("error in getComment()\n"+e.toString());
		}
	}
	
	private void getCommentUser(User[] uArray) {
		String id = "";
		Comments cm =new Comments();
		WeiboComment wc = null;
		cm.client.setToken(accessToken);
		try {
			for ( int i = 0 ; i < uArray.length ; i++ ) {
				logger.debug(uArray[i].toString());
				id = uArray[i].getStatusId();
//				System.out.println(statusArray[i].toString());
				CommentWapper comment = cm.getCommentById(id);
				int count = comment.getComments().size();
				commentArray = new Comment[count];
				int j = 0;
				
				for(Comment c : comment.getComments()){
					logger.debug(c.toString());
					wc = new WeiboComment(c);
					cDAO.insert(wc);
 					commentArray[j] = c;
					
					j++;
					totalNumber++;
				}
			}
		} catch (WeiboException e) {
			e.printStackTrace();
			logger.error("error in getCommentUser()\n"+e.toString());
		}
	}
}
