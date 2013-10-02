package spc_proj.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import weibo4j.util.WeiboConfig;

public class DbHandler {
	public Connection conn = null;
	private LogHandler logger = null;
	
	public DbHandler() {
		// TODO Auto-generated constructor stub
		this.logger = new LogHandler("DbHandler");
	}

	public void connect() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connection_string = "jdbc:mysql://"+
						WeiboConfig.getValue("DB_IP") +":" +
						WeiboConfig.getValue("DB_PORT") +"/weibo?user=" +
						WeiboConfig.getValue("DB_ID") + "&password=" +
						WeiboConfig.getValue("DB_PW");
		    
			conn = DriverManager.getConnection(connection_string);	
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch (Exception e) {
		    // handle any errors
		    e.printStackTrace();
		}
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
