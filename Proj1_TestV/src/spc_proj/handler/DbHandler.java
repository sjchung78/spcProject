package spc_proj.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.apache.logging.log4j.core.helpers.Closer;

import com.mysql.jdbc.PreparedStatement;

import weibo4j.util.WeiboConfig;

public class DbHandler {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private LogHandler logger = null;
	
	public DbHandler() {
		// TODO Auto-generated constructor stub
		this.logger = new LogHandler("DbHandler");
		connect();
	}

	public void connect() {
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String connection_string = "jdbc:mysql://"+
							WeiboConfig.getValue("DB_IP") +":" +
							WeiboConfig.getValue("DB_PORT") +"/" + WeiboConfig.getValue("schema") + "?useUnicode=true&characterEncoding=utf-8";
			    
				conn = DriverManager.getConnection(connection_string, WeiboConfig.getValue("DB_ID"), WeiboConfig.getValue("DB_PW"));
			}
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    conn = null;
		}
		catch (Exception e) {
		    // handle any errors
		    e.printStackTrace();
		    conn = null;
		}
	}
	public boolean update(String sql){
		closeRsStmt();
		try{
			stmt = conn.createStatement();
			try{
				stmt.executeUpdate(sql);
			}catch(SQLException ex){
				ex.printStackTrace();
				logger.error("Can't excuteUpdate!");
				return false;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("Can't createStatement!");
			return false;
		}
		return true;
	}
	public boolean insert(String sql){
		closeRsStmt();
		try{
			stmt = conn.createStatement();
			try{
				stmt.executeUpdate(sql);
			}catch(SQLException ex){
				ex.printStackTrace();
				logger.error("Can't excuteUpdate:" + sql);
				return false;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("Can't createStatement:" + sql);
			return false;
		}
		return true;
	}
	public ResultSet query(String sql){
		closeRsStmt();
		try{
			stmt = conn.createStatement();
			try{
				rs = stmt.executeQuery(sql);
			}catch(SQLException ex){
				ex.printStackTrace();
				logger.error("Can't excuteQuery!");
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("Can't createStatement!");
			return null;
		}
		return rs;
	}
	private void closeStmt(){
		try{
			if (stmt != null){
				stmt.close();
				stmt = null;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			logger.error("Can't close statement!");
		}
	}
	private void closeRsStmt(){
		try{
			if (rs != null){
				rs.close();
				rs = null;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			logger.error("Can't close resultset!");
		}finally{
			closeStmt();
		}
	}
	public boolean close(){
		closeRsStmt();
		try{
			if (conn != null){
				conn.close();
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			logger.error("Can't close connection");
			return false;
		}
		return true;
	}
	
	public void select() {
		Statement stmt = null;
		ResultSet rs = null;

		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM user1");

		    // or alternatively, if you don't know ahead of time that
		    // the query will be a SELECT...

//		    if (stmt.execute("SELECT foo FROM bar")) {
//		        rs = stmt.getResultSet();
//		    }

		    // Now do something with the ResultSet ....
//		    System.out.println(rs.getString(0));
		    System.out.println(rs.findColumn("id"));
		    if(rs.first())
		    	System.out.println(rs.getString(1));
		    rs.next();
		    
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed

		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore

		        rs = null;
		    }

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore

		        stmt = null;
		    }
		}
	}
}
