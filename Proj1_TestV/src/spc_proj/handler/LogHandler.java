package spc_proj.handler;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LogHandler {

	String LogName = "";
	Logger logger = null;
	
	public LogHandler(String name) {
		LogName = name;
		logger = LogManager.getLogger(LogName);
	}

	public void debug(String message) {
		// TODO Auto-generated method stub
		logger.debug("[DEBUG] [" +LogName+"] "+message);
	}

	public void error(String message) {
		// TODO Auto-generated method stub
		logger.error("[ERROR] [" +LogName+"] "+message);
	}

	public void fatal(String message) {
		// TODO Auto-generated method stub
		logger.fatal("[FATAL] [" +LogName+"] "+message);
	}

	public void info(String message) {
		// TODO Auto-generated method stub
		logger.info("[INFO ] [" +LogName+"] "+message);
	}

	public void warn(String message) {
		// TODO Auto-generated method stub
		logger.warn("[WARN ] [" +LogName+"] "+message);
	}

	public Logger getLog(){
		return logger;
	}
}
