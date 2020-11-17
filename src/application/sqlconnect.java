package application;

import java.sql.*;

import javax.swing.JOptionPane;

public class sqlconnect {
	
	static Connection conn;
	
	public static Connection dbconnect() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:BankingSystem.db");
			JOptionPane.showMessageDialog(null, "database connected");
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
