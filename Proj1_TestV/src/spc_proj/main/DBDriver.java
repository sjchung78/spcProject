package spc_proj.main;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.sql.*;
import spc_proj.handler.LogHandler;

import org.codehaus.jackson.map.ser.impl.StaticListSerializerBase;

import sun.security.util.Password;

public class DBDriver {
	public static String url = "jdbc:mysql://localhost:3306/testdb";
	public static String user = "root";
	public static String password = "root";
	public Connection con = null;         
	Statement st = null;
    ResultSet rs = null;

	public DBDriver(){
		LogHandler log = new LogHandler();
		try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

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
                Logger lgr = Logger.getLogger(Version.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

	}
}
