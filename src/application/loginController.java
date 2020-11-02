package application;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
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




public class loginController {
    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtd;

    @FXML
    private Button butlogin;

    @FXML
    private Button butsignin; 	// go back to sign in
    
    loginmodel user = new loginmodel();
    static String username;
    static public String username() {
    	return username;
    }
    
    @FXML
    public void login(ActionEvent event) {
    	
    	try {
			if(user.isLogin(txtd.getText(), txtpassword.getText())) {
				JOptionPane.showMessageDialog(null, "Welcome, "+user.getusername());
				username = user.getusername();
				if(user.getusername().equals("admin")){
					// if user is admin
					try {
						
						((Node)event.getSource()).getScene().getWindow().hide();
						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						Pane root;
						root = loader.load(getClass().getResource("/admin/admin.fxml").openStream());
						Scene scene = new Scene(root);
						
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						System.out.println("not able to load admin.fxml - loginController.login()");
					}				
				
				}else {
					//if any normal user
					
					try {
						
						((Node)event.getSource()).getScene().getWindow().hide();
						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader();
						Pane root;
						root = loader.load(getClass().getResource("/user/user.fxml").openStream());
						Scene scene = new Scene(root);
						
						primaryStage.setScene(scene);
						primaryStage.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						System.out.println("not able to load admin.fxml - loginController.login()");
					}
					
				}
				
			}else {
				JOptionPane.showMessageDialog(null, "Username or Password is INCORRECT.");
				txtd.setText("");
				txtpassword.setText("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("error in sql connection - logincontroller.login");
		}
    	
    }
    
    @FXML
    public void backtosignin(ActionEvent event) {
    	try {
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root;
			root = loader.load(getClass().getResource("/application/signin.fxml").openStream());
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("not able to load signin.fxml - loginController.backtologin()");
		}
    }
}
