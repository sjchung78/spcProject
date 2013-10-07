package spc_proj.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class StringUtil {

	public static String getCurrent(int type) {
		/*
		 * type 1 : yyyymmddhh24miss
		 * type 2 : mmddyyyy
		 * type 3 : hh24miss
		 */
		TimeZone zone = TimeZone.getTimeZone("EST");
		Calendar cur = Calendar.getInstance(zone);
		
		SimpleDateFormat sdf = null;
		
		switch (type){
		case 1:
			sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			break;
		case 2:
			sdf = new SimpleDateFormat("MMddyyyy");
			break;
		case 3:
			sdf = new SimpleDateFormat("HHmmss");
			break;
		default:
			sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		}
		
	    String TimeStop_Str = sdf.format(cur.getTime());
	    
		return TimeStop_Str;
	}

}
