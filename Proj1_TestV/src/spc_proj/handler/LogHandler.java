package spc_proj.handler;

import org.apache.log4j.Logger;

public class LogHandler {

	String LogName = "";
	Logger logger = null;
	
	public LogHandler(String name) {
		LogName = name;
		logger = Logger.getLogger(LogName);
	}

	public void debug(String message) {
		// TODO Auto-generated method stub
		logger.debug("[" +LogName+"] "+message);
	}

	public void error(String message) {
		// TODO Auto-generated method stub
		logger.error("[" +LogName+"] "+message);
	}

	public void fatal(String message) {
		// TODO Auto-generated method stub
		logger.fatal("[" +LogName+"] "+message);
	}

	public void info(String message) {
		// TODO Auto-generated method stub
		logger.info("[" +LogName+"] "+message);
	}

	public void warn(String message) {
		// TODO Auto-generated method stub
		logger.warn("[" +LogName+"] "+message);
	}

	public Logger getLog(){
		return logger;
	}
}
