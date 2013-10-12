package spc_proj.wrapper;

import java.net.URL;
import java.util.Date;

import spc_proj.utils.StringUtil;
import weibo4j.model.Status;
import weibo4j.model.User;

public class WeiboConnection {
	private String screenNameA = null;
	private String screenNameB = null;
	private int relation = 0;
	
	
	public WeiboConnection(Status s) {
		// TODO Auto-generated constructor stub
		
		//this.id = s.getUser().getId();
	}
	

	public WeiboConnection(String screenNameA, String screenNameB, int relation) {
		super();
		this.screenNameA = screenNameA;
		this.screenNameB = screenNameB;
		this.relation = relation;
	}


	public WeiboConnection(User u) {
		// TODO Auto-generated constructor stub
	//	this.id = u.getId();
	//	this.screen_name = u.getScreenName();
	}


	public int getRelation() {
		return relation;
	}


	public String getScreenNameA() {
		return screenNameA;
	}


	public String getScreenNameB() {
		return screenNameB;
	}


	public void setRelation(int relation) {
		this.relation = relation;
	}


	public void setScreenNameA(String screenNameA) {
		this.screenNameA = screenNameA;
	}
	
	public void setScreenNameB(String screenNameB) {
		this.screenNameB = screenNameB;
	}
	
	
}
