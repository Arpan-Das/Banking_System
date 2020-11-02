package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.*;

import javax.swing.JOptionPane;

public class signinController {
	dbconnect db = new dbconnect();
	Connection conn = db.connect();
	PreparedStatement pstmt;
	
    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtusername;

    @FXML
    private TextField txtlastname;

    @FXML
    private TextField txtfirstname;
    
    @FXML
    private TextField txtmobileno;

    @FXML
    private TextField txtemail;

    @FXML
    private Button butregister;

    @FXML
    private Button butlogin;	// go back to log in page
    
    
	
    @FXML
    public void register(ActionEvent event) {
    	String firstname = txtfirstname.getText();
    	String lastname = txtlastname.getText();
    	String email = txtemail.getText();
    	String mobileno = txtmobileno.getText();
    	String username = txtusername.getText();
    	String password = txtpassword.getText();
    	
    	
    	
		try {
			pstmt = conn.prepareStatement("INSERT INTO temp(firstname, lastname,email,mobileno, username, password) VALUES(?,?,?,?,?,?)");
			pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, email);
            pstmt.setString(4, mobileno);
            pstmt.setString(5, username);
            pstmt.setString(6, password);
            
            int status = pstmt.executeUpdate();
            if(status == 1) {
            	JOptionPane.showMessageDialog(null, "data inserted successfully.");
            	txtfirstname.setText("");
            	txtlastname.setText("");
            	txtusername.setText("");
            	txtpassword.setText("");
            	txtemail.setText("");
            	txtmobileno.setText("");
            }else {
            	JOptionPane.showMessageDialog(null, "data inserted unsuccessfully.");
            }
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("error in prepareStatement- signinController.register");
		}
            
    	
    }
    
    @FXML
    public void backtologin(ActionEvent event) {	// go back to log in page
    	try {
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root;
			root = loader.load(getClass().getResource("/application/login.fxml").openStream());
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("not able to load login.fxml - signinController.backtosignin()");
		}
    }
}
