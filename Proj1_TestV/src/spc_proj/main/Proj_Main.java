package spc_proj.main;

import java.sql.Connection;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import weibo4j.util.WeiboConfig;

public class Proj_Main {
	
	/**
	 * @param args  
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		LogHandler logger = new LogHandler("Proj_Main");
		
		String accessToken = WeiboConfig.getValue("access_token1");
		LogHandler logger1 = new LogHandler("Thread1");
		Proj_thread pth1 = new Proj_thread(accessToken, logger1, 1);
		pth1.start();
		
		accessToken = WeiboConfig.getValue("access_token2");
		LogHandler logger2 = new LogHandler("Thread2");
		Proj_thread pth2 = new Proj_thread(accessToken, logger2, 2);
		pth2.start();

		accessToken = WeiboConfig.getValue("access_token3");
		LogHandler logger3 = new LogHandler("Thread3");
		Proj_thread pth3 = new Proj_thread(accessToken, logger3, 3);
		pth3.start();
		
		int i = 0;
		while (true) {
			try {
				Thread.sleep(10000);
				logger.debug("i["+ (i++) +"]-th");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
	}

}
