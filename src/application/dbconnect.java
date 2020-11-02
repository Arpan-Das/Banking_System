package application;

import java.sql.*;

public class dbconnect {
	public Connection connect() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:AV.db");
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Database not connected error-dbconnect");
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("database class not found error - dbconnect");
			return null;
		}
	}
}
