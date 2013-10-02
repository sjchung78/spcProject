package spc_proj.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import spc_proj.handler.LogHandler;
import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;
import weibo4j.util.BareBonesBrowserLaunch;
import weibo4j.util.WeiboConfig;

public class Proj1Main {
	
	/**
	 * @param args  
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		LogHandler logger = new LogHandler("Proj1Main");

		Oauth oauth = new Oauth();
		String access_token = WeiboConfig.getValue("access_token");
		Timeline tm = new Timeline();
		tm.client.setToken(access_token);
		try {
			StatusWapper status = tm.getPublicTimeline();
			for(Status s : status.getStatuses()){
				logger.info(s.toString());
			}
			System.out.println(status.getNextCursor());
			System.out.println(status.getPreviousCursor());
			System.out.println(status.getTotalNumber());
			System.out.println(status.getHasvisible());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
