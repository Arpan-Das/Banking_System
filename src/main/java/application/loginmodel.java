package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginmodel {
	String username;
	
	public boolean isLogin(String user, String pass) throws SQLException {
		dbconnect db = new dbconnect();
		Connection conn = db.connect();
		PreparedStatement preparedStatment = null;
		ResultSet resultSet= null;
		String query = "select * from main where username = ? and password = ?";
		try {
			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setString(1, user);
			preparedStatment.setString(2, pass);
			resultSet = preparedStatment.executeQuery();
			if(resultSet.next()) {
				username = resultSet.getString("username");
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		} finally {
			preparedStatment.close();
			resultSet.close();
		}
	}
	
	public String getusername() {
		return username;
	}
}
