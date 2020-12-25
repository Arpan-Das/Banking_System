package User;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import application.sqlconnect;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
	
public class FirstTimeLogin implements Initializable {

	@FXML
	private Label lab_name, lab_username;
	
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
	private AnchorPane verifyotp;
	private String otp;
	@FXML private TextField txt_enterotp;
	
	@FXML
	void exxit(ActionEvent event) {
		System.exit(0);	
	}

	public void SetName(String user) {
		lab_name.setText(user);
	}
	public void setAccno(int accno) {
		accountnumber.setText(String.valueOf(accno));
	}
	public void setUsername(String username) {
		lab_username.setText(username);
	}
	public void setEmailid(String emailid) {
		email.setText(emailid);
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
			//******************* when user enter all the details*****************************************
			// save the details to the data base user
			
			if(newpass.getText().equals("0000")) {
				JOptionPane.showMessageDialog(null, "New Password can't be your Default Password");
				newpass.setText("");
			}else {
				
				otp = random();
				sendOTP(email.getText(), otp);
				verifyotp.setVisible(true);
			}
			//********************************************************************************************
		}
	}
	
	public void resend() {
		otp = random();
		sendOTP(email.getText(), otp);
	}
	
	public void verify(ActionEvent event) {
		if(txt_enterotp.getText().equals(otp)){
			//************************** storing data into database *********************
			Connection conn = sqlconnect.dbconnect();
			try {
				String sql = "update user set dob = '"+dob.getValue()+"' , address = '"+address.getText()+"' , emailid ='"+email.getText()+"' , password = '"+newpass.getText()+"' where accno = "+accountnumber.getText()+" ";
				Statement stmt = conn.createStatement();
				stmt.execute(sql);
				JOptionPane.showMessageDialog(null, "Password changed successfully - login using new password");
			}catch(Exception e) {
				
			}
			//************************** open the BankingSystem *****************************
			try {
//		   		((Node)event.getSource()).getScene().getWindow().hide();
//		   		Stage primaryStage = new Stage();
//		   		FXMLLoader loader = new FXMLLoader();
//		   		Pane root = loader.load(getClass().getResource("/User/UserPanel.fxml").openStream());
//		   		Scene scene = new Scene(root);
//		   		primaryStage.setScene(scene);
//    		
//		   		UserPanelController userpanelController = (UserPanelController)loader.getController();
//		   		userpanelController.setAccno(Integer.valueOf(accountnumber.getText()));
//		   		userpanelController.setName(lab_name.getText())	;
//		   		userpanelController.setUsername(lab_username.getText());
//			
//		   		primaryStage.initStyle(StageStyle.TRANSPARENT);
//		   		primaryStage.show();
				
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
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		lab_username.setVisible(false);
		verifyotp.setVisible(false);
	}
	
	public String random() {
		Random rand = new Random();
		return String.valueOf(rand.nextInt(10000 + 1));
	}
	
	public void sendOTP(String emailid, String otp) {
			Properties props=new Properties();
	        props.put("mail.smtp.host","smtp.gmail.com");
	        props.put("mail.smtp.port",465);
	        props.put("mail.smtp.user","ad0084763@gmail.com"); // your email id
	        props.put("mail.smtp.auth",true);
	        props.put("mail.smtp.starttls.enable",true);
	        props.put("mail.smtp.debug",true);
	        props.put("mail.smtp.socketFactory.port",465);
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.socketFactory.fallback",false); 
	        
	        try {             
	                Session session = Session.getDefaultInstance(props, null);
	                session.setDebug(true);
	                MimeMessage message = new MimeMessage(session);
	                message.setText("Your OTP is " + otp);	// msg send to the email id
	                message.setSubject("AV Bank ... Bank of Benifits - Email Verfication");
	                message.setFrom(new InternetAddress("ad0084763@gmail.com"));
	                message.addRecipient(RecipientType.TO, new InternetAddress(emailid));		// email of the reciever
	                message.saveChanges();
	                try
	                {
	                Transport transport = session.getTransport("smtp");
	                transport.connect("smtp.gmail.com","ad0084763@gmail.com","Light@2020");
	                transport.sendMessage(message, message.getAllRecipients());
	                transport.close();
	                              
	                JOptionPane.showMessageDialog(null,"OTP send to your Email id"); 
	                }catch(Exception e)
	                {
	                    JOptionPane.showMessageDialog(null,e);
	                    verifyotp.setVisible(false);
	                }              
	            
	        } catch (Exception e) {
	            e.printStackTrace();  
	            JOptionPane.showMessageDialog(null,e);
	        }  

		}
}
