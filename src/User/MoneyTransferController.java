package User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.sendotp;
import application.sqlconnect;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class MoneyTransferController implements Initializable {
	@FXML
	private TextField anumber;
	@FXML
	private TextField amount;
	@FXML
	private PasswordField password;
	@FXML
	private TextField remark;
	
	@FXML
	private AnchorPane verification;
	@FXML
	private Pane onlinetransfer;
	@FXML
	private TextField otp;
	@FXML
	private TextField captcha;
	@FXML
	private Label lab_randcaptcha;
	
	@FXML
	private Label name, accno;
	
	Connection conn;
	PreparedStatement ps;
	Statement stmt;
	ResultSet rs;
	
	String randotp, randcaptcha, email;
	String b_username, b_name;
	
	public String random() {
		Random rand = new Random();
		return String.valueOf(rand.nextInt(1000+1));
	}
	
	public void setloan(String acconumber, String amountt){
    	anumber.setText(acconumber);
    	amount.setText(amountt);
    	amount.setEditable(false);
    	anumber.setEditable(false);
    	
    }
	
	@FXML
	public void submit(ActionEvent event) {
		//**************************** main code *********************************
		if(anumber.getText().trim().isEmpty() || amount.getText().trim().isEmpty() || password.getText().trim().isEmpty()) {
			
			JOptionPane.showMessageDialog(null, "Enter all the Mandatary Field");
		}else {
			//********** verify the account number *****************************
			conn = sqlconnect.dbconnect();
			try {
				ps = conn.prepareStatement("select * from user where accno = ? ");
				ps.setString(1, data.getAccno());
				rs = ps.executeQuery();
				String pass = rs.getString("password");
				if(password.getText().equals(pass)){	//************** verify the password
					try {
			
						ps = conn.prepareStatement("select * from user where accno = ? ");
						ps.setString(1, anumber.getText());
						rs = ps.executeQuery();
				
						if(rs.next()) {	//********** verify the account number
							b_username = rs.getString("username");
							b_name = rs.getString("firstname")+" "+rs.getString("lastname"); 
						
							try {
								stmt = conn.createStatement();
								rs = stmt.executeQuery("select * from user where accno = "+data.getAccno());
								email = rs.getString("emailid");
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null, "email id -->>"+e);
							}
					
					
							//**************** send otp + generate the captcha + open the verify ancherpane *************************
							randotp = random();
					
							String msg = "Alert!!!!\nYou have initialize a online transection of Rs."+ amount.getText() + " to Account No. "+anumber.getText()+".\nIf not done by you PLEASE contact Admin.\n";
							if(sendotp.sendOTP(msg, email, randotp)) {
					
								randcaptcha = random();
								lab_randcaptcha.setText(randcaptcha);
					
								verification.setVisible(true);
								onlinetransfer.setVisible(false);
							}
						}else {
							JOptionPane.showMessageDialog(null, "Please Enter a valid Account number.");
						}
						
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "accountnumber -->>"+e);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Please enter valid password");
				}
				conn.close();
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(null, "password -->>"+e);
			}
		}
	}
	
	
	@FXML
	public void generateRandom(ActionEvent event) {		//******************** referesh the captcha **********
		// ********************* captcha ***********************************
		randcaptcha = random();
		lab_randcaptcha.setText(randcaptcha);
	}

	
	@FXML
	public void Verify(ActionEvent event) {
		//***************************** verify otp and captcha + store the data in the respected table **************************************
		
		if(otp.getText().equals(randotp)) {
			if(captcha.getText().equals(randcaptcha)) {
				
				//*************(debit from user) update the balance of user and update the trx table **************

				conn = sqlconnect.dbconnect();
				try {
					// to get the previous balance
					String query = "select * from "+ data.getUsername()+data.getAccno();
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					float balance = rs.getFloat("balance") ;
							
					if(balance > Float.valueOf(amount.getText()) ) {
				
						//********************** insert data into trx. table *******************************
				
						stmt.execute("update "+data.getUsername()+data.getAccno()+"  set balance = " + (balance - Float.valueOf(amount.getText())));
				
						stmt.execute("create table IF NOT EXISTS temp (date text, remarks text, type text, amount real, balance real)");
				
						ps = conn.prepareStatement("insert into temp values(datetime('now','localtime'), ?, ?, ?,?)");
						ps.setString(1, "online trx. to "+anumber.getText()+"/"+b_name +" - "+remark.getText());
						ps.setString(2, "Debit");
						ps.setFloat(3, Float.valueOf(amount.getText()));
						ps.setFloat(4, (balance - Float.valueOf(amount.getText())));
						ps.execute();
				
						stmt.execute("insert into temp select * from trx" +data.getUsername()+data.getAccno() );
				
						stmt.execute("drop table trx" + data.getUsername()+data.getAccno());
				
						stmt.execute("alter table temp rename to trx" + data.getUsername()+data.getAccno());

						
						//*************(credit to benificiary) update the balance of benificiary and update the trx table **********
						
						try {
							// to get the previous balance
							String query2 = "select * from "+ b_username+anumber.getText();
							stmt = conn.createStatement();
							rs = stmt.executeQuery(query2);
							float balance2 = rs.getFloat("balance") ;
									
							stmt.execute("update "+b_username+anumber.getText()+" set balance = " + (balance2 + Float.valueOf(amount.getText())));
						
							//********************** insert data into trx. table *******************************
							
							stmt.execute("create table IF NOT EXISTS temp (date text, remarks text, type text, amount real, balance real)");
						
							ps = conn.prepareStatement("insert into temp values(datetime('now','localtime'), ?, ?, ?,?)");
							ps.setString(1, "online trx. from "+data.getAccno()+"/"+data.getName() +" - "+remark.getText());
							ps.setString(2, "Credit");
							ps.setFloat(3, Float.valueOf(amount.getText()));
							ps.setFloat(4, (balance2 + Float.valueOf(amount.getText())));
							ps.execute();
						
							stmt.execute("insert into temp select * from trx" +b_username+anumber.getText() );
						
							stmt.execute("drop table trx" + b_username+anumber.getText());
						
							stmt.execute("alter table temp rename to trx"+b_username+anumber.getText());
						
							JOptionPane.showMessageDialog(null, "Transection Successfully.");

						} catch (SQLException e) {
							
							JOptionPane.showMessageDialog(null, "credit"+e);
						}
						conn.close();
					}else {
						JOptionPane.showMessageDialog(null, "Insufficient Balance");
						verification.setVisible(false);
						onlinetransfer.setVisible(true);
					}

				conn.close();	
				} catch (SQLException e) {
					
					JOptionPane.showMessageDialog(null, "debit"+e);
				}
				
				//******* 
				verification.setVisible(false);
				onlinetransfer.setVisible(true);
				anumber.setText("");
				amount.setText("");
				password.setText("");
				remark.setText("");
				
			}else {
				JOptionPane.showMessageDialog(null, "Please enter the correct captcha");
			}
		}else {
			JOptionPane.showMessageDialog(null, "Please enter the correct OTP.");
		}
		
	}
	
	@FXML
	public void exxit(ActionEvent event) {
		System.exit(0);
	}
	
	@FXML
	public void out1(ActionEvent event) {	//*********************** go back to user panel ****************
		try {
    		
    		((Node)event.getSource()).getScene().getWindow().hide();
    		Stage primaryStage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/User/UserPanel.fxml").openStream());
    		
    		UserPanelController panelController = (UserPanelController)loader.getController();
    		panelController.setName(data.getName());
    		panelController.setUsername(data.getUsername());
    		panelController.setAccno(Integer.parseInt(data.getAccno()));
    		
    		Scene scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.initStyle(StageStyle.TRANSPARENT);
    		primaryStage.show();
    		
    	} catch (Exception e) {
    		
    	}
	}
	
	
	@FXML
	public void out(ActionEvent event) {	//********************** logout *****************************
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


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		verification.setVisible(false);
		onlinetransfer.setVisible(true);
		name.setText(data.getName());
		accno.setText(data.getAccno());
	}
}
