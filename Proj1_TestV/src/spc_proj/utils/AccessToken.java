package spc_proj.utils;

import java.util.Vector;

import weibo4j.util.WeiboConfig;

public class AccessToken {
	public static String[] getAll(){
		String accessTokens = WeiboConfig.getValue("access_token");
		return accessTokens.split(",");
	}
	
}
