package spc_proj.main;

import org.apache.log4j.Logger;
import spc_proj.handler.LogHandler;

public class Proj1Main {
	
//	static LogHandler Log = new LogHandler("ProjMain");

	/**
	 * @param args  
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Proj1Main pm = new Proj1Main();
		LogHandler Log = new LogHandler("ProjMain");
		Logger logger = Log.getLog();
		logger.info("test log");
	}

}
