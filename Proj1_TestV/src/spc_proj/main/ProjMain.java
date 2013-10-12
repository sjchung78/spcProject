package spc_proj.main;

import java.sql.Connection;

import spc_proj.handler.DbHandler;
import spc_proj.handler.LogHandler;
import spc_proj.utils.AccessToken;
import weibo4j.util.WeiboConfig;
public class ProjMain {
	
	/**
	 * @param args  
	 */
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		LogHandler logger = new LogHandler("Proj_Main");
		
		String[] accessTokens = AccessToken.getAll();

		for (int i = 0; i < accessTokens.length; i++){
			new ProjThread(accessTokens[i], new LogHandler("Thread" + i), i%ProjThread.totalTypes).start();
		}
		
	}

}
