package spc_proj.main;

import weibo4j.Friendships;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.WeiboConfig;

public class GetFriendsByID {

	public static void main(String[] args) {
		String id = "1981622273";
		Friendships fm = new Friendships();
		fm.client.setToken(WeiboConfig.getAccessToken());
		try {
			UserWapper users = fm.getFriendsByID(id);
			for(User u : users.getUsers()){
				System.out.println(u.toString());
			}
			System.out.println(users.getNextCursor());
			System.out.println(users.getPreviousCursor());
			System.out.println(users.getTotalNumber());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
