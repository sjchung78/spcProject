package spc_proj.utils;

import weibo4j.util.WeiboConfig;

public class WConfig {
	public static int minNumToCraw = Integer.parseInt(WeiboConfig.getValue("minNumToCraw"));
	public static int saveRows = Integer.parseInt(WeiboConfig.getValue("saveRows"));
	public static String seperator = "-|+|-";
}
