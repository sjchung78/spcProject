package spc_proj.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeiboTime {
	   //get current date time with Date()
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static String getTime(){
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}
}
