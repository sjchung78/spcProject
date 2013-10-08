package spc_proj.main;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.sql.*;
import spc_proj.handler.LogHandler;

import org.codehaus.jackson.map.ser.impl.StaticListSerializerBase;


public class DBDriver {
	public static String url = "jdbc:mysql://localhost:3306/testdb";
	public static String user = "root";
	public static String password = "root";
	public Connection con = null;         
	Statement st = null;
    ResultSet rs = null;

	public DBDriver(){
		LogHandler log = new LogHandler("DBHandler");
		try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
        	log.error(ex.getMessage());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
            	log.error(ex.getMessage());
            }
        }
    }
}
