package application;

import java.sql.*;

import javax.swing.JOptionPane;

import Admin.user;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	
	public static ObservableList<user> getDatauser(){
		Connection conn = dbconnect();
		ObservableList<user> list = FXCollections.observableArrayList();
		
		try {
			
			//PreparedStatement ps = conn.prepareStatement("select * from user");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user");
			
			while(rs.next()) {
				list.add(new user(rs.getInt("accountnumber"), rs.getString("name"), rs.getString("gender"), rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getInt("mobilenumber")));
				
			}
						
		}catch(Exception e) {
			
		}
		return list;
	}

     
}
