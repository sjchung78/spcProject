package spc_proj.main;

import java.sql.Connection;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import weibo4j.util.WeiboConfig;
import weibo4j.util.AccessToken;
public class Proj_Main {
	
	/**
	 * @param args  
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		LogHandler logger = new LogHandler("Proj_Main");
		
		String[] accessTokens = AccessToken.getAll();

		for (int i = 0; i < accessTokens.length; i++){
			new Proj_thread(accessTokens[i], new LogHandler("Thread" + i), i).start();
		}
		
		
		int i = 0;
		while (true) {
			try {
				Thread.sleep(100000);
				logger.debug("i["+ (i++) +"]-th");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
	}

}
