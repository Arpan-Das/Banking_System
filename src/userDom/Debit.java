package userDom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import User.data;
import application.sqlconnect;

public class Debit {
	public static boolean debit(double amount,String remark) {
		Connection conn;
		Statement stmt;
		ResultSet rs;
		PreparedStatement prst;
		//************************************* code for withdraw (debit)**************************
		
		conn = sqlconnect.dbconnect();
		try {
			// to get the previous balance
			String query = "select * from "+ data.getUsername()+data.getAccno();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			double balance = rs.getDouble("balance") ;
					
			if(balance > amount ) {
				
				//********************** update the previous balance *******************************
						
				stmt.execute("update "+data.getUsername()+data.getAccno()+"  set balance = " + (balance - amount));
				
				//********************** insert data into trx. table *******************************
				
				stmt.execute("create table IF NOT EXISTS temp (date text, remarks text, type text, amount real, balance real)");
		
				prst = conn.prepareStatement("insert into temp values(datetime('now','localtime'), ?, ?, ?,?)");
				prst.setString(1, remark);
				prst.setString(2, "Debit");
				prst.setDouble(3, amount);
				prst.setDouble(4, (balance - amount));
				prst.execute();
		
				stmt.execute("insert into temp select * from trx" +data.getUsername()+data.getAccno() );
		
				stmt.execute("drop table trx" + data.getUsername()+data.getAccno());
		
				stmt.execute("alter table temp rename to trx" + data.getUsername()+data.getAccno());

				
//				JOptionPane.showMessageDialog(null, "Transection Successfully.");	
				conn.close();
				return true;
				
			}else {
				JOptionPane.showMessageDialog(null, "Insufficient Balance");
			}
		
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, "debit"+e);
		}
		return false;
		
	}
}
