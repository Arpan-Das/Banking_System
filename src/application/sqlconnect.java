package application;

import java.sql.*;

import javax.swing.JOptionPane;

import Admin.complaints;
import Admin.fixeddeposit;
import Admin.loansapplied;
import Admin.user;
import User.activeloans;
import User.activitylog;
import User.deposit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class sqlconnect {
	
	static Connection conn;
	
	public static Connection dbconnect() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:BankingSystem.db");
		//	JOptionPane.showMessageDialog(null, "database connected");
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
			conn.close();			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "sqlldsjf "+e);
		}
		return list;
	}

    public static ObservableList<complaints>getDatacomplaints(){
		conn = sqlconnect.dbconnect();
		ObservableList<complaints> list = FXCollections.observableArrayList();
    	try {
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("select * from feed_Comp");
    		while(rs.next()) {
    			list.add(new complaints(rs.getInt("accno"), rs.getString("type"), rs.getString("remark"), rs.getString("status")));
    		}
    		conn.close();
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, e);
    	}
    	return list;
    	
    }
     

    public static ObservableList<loansapplied>getDataloansapplied(){
    	conn = sqlconnect.dbconnect();
    	ObservableList<loansapplied> list = FXCollections.observableArrayList();
    	try {
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("select *from loan");
    		while(rs.next()) {
    			list.add(new loansapplied(rs.getInt("accno"), rs.getFloat("amount"), rs.getString("username"),
    					rs.getString("status"), rs.getString("why")));
    		}
    		conn.close();
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, "inside getDataLoansappied()----"+e);
    	}
		return list;
    }


	public static ObservableList<activitylog> getDatalog(String user, String anumber) {
		conn = sqlconnect.dbconnect();
		ObservableList<activitylog> list = FXCollections.observableArrayList();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM trx" + user + anumber;
			ResultSet rs= stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new activitylog(rs.getDouble("amount"), rs.getString("type"), rs.getString("remarks"),
						rs.getString("date"), rs.getDouble("balance")));
			}
			conn.close();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "inside getDatalog()---"+e);
		}
		return list;
		
	}
   

	public static ObservableList<activeloans> getDataloans(int accountnumber) {
		
		conn = sqlconnect.dbconnect();
		ObservableList<activeloans> list = FXCollections.observableArrayList();
		try {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM loan where accono =\"" + accountnumber + "\"";
			ResultSet rs= stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new activeloans(rs.getDouble("amount"), rs.getString("type"), rs.getString("duedate"), rs.getDouble("dueamount"),
						rs.getDouble("topay"), rs.getDouble("paid"), rs.getString("remark"), rs.getString("applieddate")));
			}
			conn.close();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "inside getDatalog()---"+e);
		}
		return list;
	}


	public static ObservableList<fixeddeposit> getDatafixeddeposits() {
		conn = sqlconnect.dbconnect();
    	ObservableList<fixeddeposit> list = FXCollections.observableArrayList();
    	try {
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("select *from fixeddeposits");
    		while(rs.next()) {
    			list.add(new fixeddeposit(rs.getDouble("amount"), rs.getString("username"), rs.getString("mdate"), rs.getDouble("rate"),
    					rs.getDouble("profit"), rs.getInt("accountnumber"), rs.getString("applieddate")));
    		}
    		conn.close();
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, "inside getDataLoansappied()----"+e);
    	}
		return list;
      }


	public static ObservableList<deposit> getDatadeposits(int parseInt) {
		conn = sqlconnect.dbconnect();
    	ObservableList<deposit> list = FXCollections.observableArrayList();
    	try {
    		Statement stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("select *from fixeddeposits where accountnumber = \"" + parseInt + "\"");
    		while(rs.next()) {
    			list.add(new deposit(rs.getDouble("amount"), rs.getString("mdate"), rs.getDouble("rate"),
    					rs.getDouble("profit"), rs.getString("applieddate")));
    		}
    		conn.close();
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, "inside getDataLoansappied()----"+e);
    	}
		return list;
      }
	}
