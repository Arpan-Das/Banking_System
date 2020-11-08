package admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import application.dbconnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class adminController {
	dbconnect db = new dbconnect();
	Connection conn = db.connect();
	Statement stmt ;
    ResultSet rs;
    PreparedStatement preparedstatement;
    String sql = "SELECT *FROM temp";
    String delete = "delete from temp where username = ?";
    String copy = "insert into main select *from temp where username = ?";
	
	@FXML
    private Button butlogout;

    @FXML
    private Button butapprove;

    @FXML
    private Label labstatus;

    @FXML
    private Label labname;

    @FXML
    private Label labmobileno;

    @FXML
    private Label labemail;

    @FXML
    private Button butreject;
    
    @FXML
    private Button butnext;
    
    @FXML
    private Button butpending;
    
    public void clickpending(ActionEvent event	) {
    	// to just connect to the table in the database
    	
		try {
			stmt = conn.createStatement();
			rs    = stmt.executeQuery(sql);
			
			
			if(rs.next()) {
				labname.setText(rs.getString("firstname") + " " + rs.getString("lastname"));
				labmobileno.setText(rs.getString("mobileno"));
				labemail.setText(rs.getString("email"));
			}else {
	
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error in clickpending() -- adminController.java");
		}
        
    	
    	
    }
         
    public void next(ActionEvent event) {
    	// to see the next detail
    	
    	
    		try {
    				if(rs.next()) {
    					labname.setText(rs.getString("firstname") + " " + rs.getString("lastname"));
    					labmobileno.setText(rs.getString("mobileno"));
    					labemail.setText(rs.getString("email"));
    				}else {
    					labname.setText(" ");
        				labmobileno.setText(" ");
        				labemail.setText(" ");
        
    				}
			} catch (SQLException e) {
				
				System.out.println("error in next() -- admnController.java");
			}
    	
    }
    
    public void approve(ActionEvent event) {
    	// to approve the data -- to just move the current row from temp-table to main-table
    	try {
    		// moving the data from temp-table to main table
			preparedstatement = conn.prepareStatement(copy);
			preparedstatement.setString(1, rs.getString("username"));
			preparedstatement.executeUpdate();
			
			
			// delete the row from the temp table 
			preparedstatement = conn.prepareStatement(delete);
			preparedstatement.setString(1, rs.getString("username"));
			preparedstatement.executeUpdate();
			
			
			
	
			
			//resetting the lable's if their is any row otherwise make it blank
			if(rs.next()) {
				labname.setText(rs.getString("firstname") + " " + rs.getString("lastname"));
				labmobileno.setText(rs.getString("mobileno"));
				labemail.setText(rs.getString("email"));
			}else {
				labname.setText(" ");
				labmobileno.setText(" ");
				labemail.setText(" ");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
	
		}
		
    }
    
    public void reject(ActionEvent event) {
    	// to reject the current data -- to just delete the rowfrom the temp-table
    	try {
			preparedstatement = conn.prepareStatement(delete);
			preparedstatement.setString(1, rs.getString("username"));
			preparedstatement.executeUpdate();
			

			
			//resetting the lable's if their is any row otherwise make it blank
			if(rs.next()) {
				labname.setText(rs.getString("firstname") + " " + rs.getString("lastname"));
				labmobileno.setText(rs.getString("mobileno"));
				labemail.setText(rs.getString("email"));
			}else {
				labname.setText(" ");
				labmobileno.setText(" ");
				labemail.setText(" ");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
    }
	
	public void logout(ActionEvent event) {
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root;
			root = loader.load(getClass().getResource("/application/AVfirst.fxml").openStream());
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("not able to load avfirst.fxml - adminController.login()");
		}
	}
}
