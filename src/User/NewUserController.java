package User;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
	
	public class NewUserController implements Initializable {

	    @FXML
	    private Label userlbl;

	    @FXML
	    private TextField accountnumber;

	    @FXML
	    private TextField newpass;

	    @FXML
	    private TextField email;

	    @FXML
	    private TextArea address;

	    @FXML
	    private DatePicker dob;
	    
	    @FXML
	    private TextField otp;

	    @FXML
	    void exxit(ActionEvent event) {

	    	System.exit(0);	
	    }

		public void SetName(String user) {
			
			userlbl.setText(user);

	   }
	    @FXML
	    void logout(ActionEvent event) {
	    	try {
	    		
	    		((Node)event.getSource()).getScene().getWindow().hide();
	    		Stage primaryStage = new Stage();
	    		FXMLLoader loader = new FXMLLoader();
	    		Pane root = loader.load(getClass().getResource("/application/BankingSystem.fxml").openStream());
	    		Scene scene = new Scene(root);
	    		primaryStage.setScene(scene);
	    		primaryStage.initStyle(StageStyle.TRANSPARENT);
	    		primaryStage.show();
	    		
	    	} catch (Exception e) {
	    		
	    	}

	    }

	    @FXML
	    void submit(ActionEvent event) {
	    	
	    	if(email.getText().trim().isEmpty() || newpass.getText().trim().isEmpty() || address.getText().trim().isEmpty()|| dob.getValue()== null )
	    	{
	    		JOptionPane.showMessageDialog(null, "Enter all details");
	    	}
	    	else {
	    	try {
	    		
	    		((Node)event.getSource()).getScene().getWindow().hide();
	    		Stage primaryStage = new Stage();
	    		FXMLLoader loader = new FXMLLoader();
	    		Pane root = loader.load(getClass().getResource("/User/UserPanel.fxml").openStream());
	    		Scene scene = new Scene(root);
	    		primaryStage.setScene(scene);
	    		UserPanelController userpanelController = (UserPanelController)loader.getController();
				userpanelController.SetName(userlbl.getText()); 
	    		primaryStage.initStyle(StageStyle.TRANSPARENT);
	    		primaryStage.show();
	    		
	    	} catch (Exception e) {
	    		
	        	}
	    	}
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			
		}

}
