package weibo4j.util;

import java.util.Vector;

public class AccessToken {
	public static String[] getAll(){
		String accessTokens = WeiboConfig.getValue("access_token");
		return accessTokens.split(",");
	}
}
