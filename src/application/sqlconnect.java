package application;

import java.sql.*;

import javax.swing.JOptionPane;

import Admin.complaints;
import Admin.loansapplied;
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
			ResultSet rs = stmt.executeQuery("select *from user");
			String name;
			
			while(rs.next()) {
				if(rs.getString("type").equals("Client")) {
					name = rs.getString("firstname")+" "+rs.getString("lastname");
					list.add(new user(rs.getInt("accno"), name, rs.getString("gender"), rs.getString("dob"),
							rs.getString("id"), rs.getString("emailid"), rs.getString("mobileno"), 
							rs.getString("username")));
					
				}
			}
						
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "sqlldsjf "+e);
		}
		return list;
	}

    public static ObservableList<complaints>getDatacomplaints(){
		return null;
    	
    }
     

    public static ObservableList<loansapplied>getDataloansapplied(){
		return null;
    	
    }
}
