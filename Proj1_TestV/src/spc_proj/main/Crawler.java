package spc_proj.main;

import unused.Log;
import weibo4j.Friendships;
import weibo4j.Users;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Crawler {
	public static void main(String[] args) throws Exception{
		String access_token = WeiboConfig.getValue("access_token");
		
		Queue peopleQueue = new LinkedList();
		
		String screen_name = "kaifulee";
		
		Users um = new Users();
		um.client.setToken(access_token);
		try {
			User user = um.showUserByScreenName(screen_name);
			peopleQueue.add(user);
			Log.logInfo(user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		Iterator iterator = peopleQueue.iterator();

		Friendships fm = new Friendships();
		fm.client.setToken(access_token);
		try {
			//fm.getFollowersIdsById(uid, 1000, 0)
			//UserWapper users = fm.getFollowersById(uid, 20, 0);
			UserWapper users = fm.getFollowersByName(screen_name, 200, 1000);
			for(User u : users.getUsers()){
				Log.logInfo(u.toString());
			}
			System.out.println(users.getNextCursor());
			System.out.println(users.getPreviousCursor());
			System.out.println(users.getTotalNumber());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
