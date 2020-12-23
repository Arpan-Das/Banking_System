package Admin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WithrawController implements Initializable {
	
	@FXML 
	private Label lab_name;
	public void setName(String name) {
		lab_name.setText(name);
	}
	
	@FXML
	private ComboBox<String> combobox;
	@FXML
	private Label randomlbl;
	@FXML
    private TextField anumber;

    @FXML
    private TextField amount;

    @FXML
    private TextField uname;

    @FXML
    private TextField otp;

    @FXML
    private TextField captcha;

    @FXML
    private TextField remark;
    String randomotp,myrandcatcha, emailid = null;
    
    Connection conn;
    PreparedStatement prst;
    Statement stmt2;
    ResultSet rs;
    
//    public void normal() {
//    	combobox.setValue(null);
//    	anumber.setText(" ");
//    	amount.setText("");
//    	uname.setText("");
//    	remark.setText("");
//    	otp.setText("");
//    	captcha.setText("");
//    	
//    	//*******reset the captcha
//    	myrandcatcha = random();
//		randomlbl.setText(myrandcatcha);
//    }
    
    public String random() {
    	Random rand = new Random();
    	return String.valueOf(rand.nextInt(10000 + 1));
    }
    
	public void generateRandom(ActionEvent event) {
		myrandcatcha = random();
		randomlbl.setText(myrandcatcha);
	}
	
	
	ObservableList<String> list = FXCollections.observableArrayList("WITHDRAW", "DEPOSIT");
	
	public void exxit(ActionEvent event) {
		System.exit(0);	
		}

    public void out(ActionEvent event) {	//******* logout
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

    public void out1(ActionEvent event) {		///*********** go back to admin panel
	try {
		
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/Admin/AdminPanel.fxml").openStream());
		
		AdminPanelController admincomplaints = (AdminPanelController)loader.getController();
		admincomplaints.SetAdminName(lab_name.getText());
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
		
	} catch (Exception e) {
		
	}
}
   
    public void submit(ActionEvent event) {
    	if(uname.getText().trim().isEmpty() || anumber.getText().trim().isEmpty() || amount.getText().trim().isEmpty()
    		|| remark.getText().trim().isEmpty() || captcha.getText().trim().isEmpty()|| otp.getText().trim().isEmpty()
    		|| combobox.getValue().trim().isEmpty())
    	{
    		JOptionPane.showMessageDialog(null, "Enter all details properly");
			
		}else {
			
			if(otp.getText().equals(randomotp)) {
				otp.setStyle(
						"-fx-border-color: white;"
				);
								
				if(captcha.getText().equals(myrandcatcha)) {
					captcha.setStyle(
							"-fx-border-color: white;"
					);
							
					if(combobox.getSelectionModel().getSelectedItem().toString() == "WITHDRAW") {
				
						//************************************* code for withdraw (debit)**************************
				
						conn = sqlconnect.dbconnect();
						try {
							// to get the previous balance
							String query = "select * from "+ uname.getText()+anumber.getText();
							stmt2 = conn.createStatement();
							rs = stmt2.executeQuery(query);
							float balance = rs.getFloat("balance") ;
									
							if(balance > Float.valueOf(amount.getText()) ) {
						
								//********************** insert data into trx. table *******************************
						
								stmt2.execute("update "+uname.getText()+anumber.getText()+"  set balance = " + (balance - Float.valueOf(amount.getText())));
						
								stmt2.execute("create table IF NOT EXISTS temp (date text, remarks text, type text, amount real, balance real)");
						
								prst = conn.prepareStatement("insert into temp values(datetime('now','localtime'), ?, ?, ?,?)");
								prst.setString(1, remark.getText());
								prst.setString(2, "Debit");
								prst.setFloat(3, Float.valueOf(amount.getText()));
								prst.setFloat(4, (balance - Float.valueOf(amount.getText())));
								prst.execute();
						
								stmt2.execute("insert into temp select * from trx" +uname.getText()+anumber.getText() );
						
								stmt2.execute("drop table trx" + uname.getText()+anumber.getText());
						
								stmt2.execute("alter table temp rename to trx" + uname.getText()+anumber.getText());
				
						
								JOptionPane.showMessageDialog(null, "Transection Successfully.");	
								
								
							}else {
								JOptionPane.showMessageDialog(null, "Insufficient Balance");
								
							}
//						normal();
						conn.close();	
						} catch (SQLException e) {
							
							JOptionPane.showMessageDialog(null, "debit"+e);
						}
				
					}else if(combobox.getSelectionModel().getSelectedItem().toString() == "DEPOSIT") {
				
						//********************************** code for deposit(credit) ***********************
				
						conn = sqlconnect.dbconnect();
						try {
							// to get the previous balance
							String query = "select * from "+ uname.getText()+anumber.getText();
							stmt2 = conn.createStatement();
							rs = stmt2.executeQuery(query);
							float balance = rs.getFloat("balance") ;
									
							stmt2.execute("update "+uname.getText()+anumber.getText()+" set balance = " + (balance + Float.valueOf(amount.getText())));
						
							//********************** insert data into trx. table *******************************
							
							stmt2.execute("create table IF NOT EXISTS temp (date text, remarks text, type text, amount real, balance real)");
						
							prst = conn.prepareStatement("insert into temp values(datetime('now','localtime'), ?, ?, ?,?)");
							prst.setString(1, remark.getText());
							prst.setString(2, "Credit");
							prst.setFloat(3, Float.valueOf(amount.getText()));
							prst.setFloat(4, (balance + Float.valueOf(amount.getText())));
							prst.execute();
						
							stmt2.execute("insert into temp select * from trx" +uname.getText()+anumber.getText() );
						
							stmt2.execute("drop table trx" + uname.getText()+anumber.getText());
						
							stmt2.execute("alter table temp rename to trx"+uname.getText() +anumber.getText());
						
							JOptionPane.showMessageDialog(null, "Transection Successfully.");
//							normal();
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, "credit"+e);
						}
				
					}
									
				}else {			//else part of Catcha
			
					//*************** if catcha is incorrect ******************************
			
					captcha.setStyle(
					"-fx-border-color: red;"
							);
				}
			}else {			// else part of OTP
		  
				//************ if otp is incorrect ****************************
			
				otp.setStyle(
					"-fx-border-color: red;"
						);
			}
		}// outer else 
	
	}
    
    public void sendotp(ActionEvent event) {
    	
    	//**************************** code for send otp to user email id when admin click on the submit button
    	
    	conn = sqlconnect.dbconnect();
    	try {
			prst = conn.prepareStatement("select *from user where accno = ? and username = ?");
			prst.setInt(1, Integer.parseInt(anumber.getText()));
			prst.setString(2, uname.getText());
			rs = prst.executeQuery();
			
			if(rs.next()) {
				emailid = rs.getString("emailid");
				randomotp = random();
				sendOTP(emailid,randomotp);
			}else {
				JOptionPane.showMessageDialog(null, "Account Not Found.");
			}
			conn.close();
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, e);
		}
    	
    	
    	
    }
    
   
    public void sendOTP(String emailid, String otp) {
		Properties props=new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port",465);
        props.put("mail.smtp.user","avbank2@gmail.com"); // your email id
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
                message.setFrom(new InternetAddress("avbank2@gmail.com"));
                message.addRecipient(RecipientType.TO, new InternetAddress(emailid));		// email of the reciever
                message.saveChanges();
                try
                {
                Transport transport = session.getTransport("smtp");
                transport.connect("smtp.gmail.com","avbank2@gmail.com","Logitech@2");
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                              
                JOptionPane.showMessageDialog(null,"OTP send to your Email id"); 
                }catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"No Internet Connection.");
                }              
            
        } catch (Exception e) {
            e.printStackTrace();  
            JOptionPane.showMessageDialog(null,e);
        }  

	}
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	combobox.setItems(list);
    	myrandcatcha = random();
		randomlbl.setText(myrandcatcha);
		lab_name.setVisible(false);
    }

}
