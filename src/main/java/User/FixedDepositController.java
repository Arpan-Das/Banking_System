package User;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;


import application.sendMail;
import application.sqlconnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import userDom.Debit;
import userDom.RemoveComma;
import userDom.dateCalculator;
import userDom.interestCalculator;

public class FixedDepositController implements Initializable{

    @FXML
    private TextField accountnumber;

    @FXML
    private TextField username;

    @FXML
    private TextField amount;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TextField mdate;

    @FXML
    private TextField profit;

    @FXML
    private TextField rate;
    
    @FXML
    private Label adminaccountnumber;
    
    Connection conn;
    Statement stmt;
	PreparedStatement ps;
	ResultSet rs;
    
    @FXML
    void dashboad(ActionEvent event) {
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
    void exxit(ActionEvent event) {
    	System.exit(0);

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
    
    String today,maturity;
    Boolean check= false;
    
    @FXML
    void proceed(ActionEvent event) {
    	
    	if(datepicker.getValue() != null) {
    		if(!amount.getText().trim().isEmpty()) {
    			
    			//******************** if user select the date and enter the amount ***********************
    			
    			//*************************** current date *************************************** 
    			LocalDate date = java.time.LocalDate.now();
    			today  = date.getYear()+"-"+date.getMonthValue()+"-"+date.getDayOfMonth();
    			//*************************** maturity date ********************************************
    			LocalDate date2 = datepicker.getValue();
    			maturity = date2.getYear()+"-"+date2.getMonthValue()+"-"+date2.getDayOfMonth();
    			
    			long days = dateCalculator.days(maturity,today);

    			if(days < 30) {
    				//******* rate 3.25%
    				settext(3.25,days,Double.valueOf(amount.getText()),maturity);
    			}else if(days < 60) {
    				// ******* rate 4.10 %
    				settext(4.10,days,Double.valueOf(amount.getText()),maturity);
    			}else if(days < 120) {
    				//******** rate 4.50%
    				settext(4.50,days,Double.valueOf(amount.getText()),maturity);
    			}else if(days < 210) {
    				//******* rate 5.40%
    				settext(5.40,days,Double.valueOf(amount.getText()),maturity);
    			}else if(days < 364) {
    				//******* rate 6.15%
    				settext(6.15,days,Double.valueOf(amount.getText()),maturity);
    			}else {
    				//******* rate 7.00%
    				settext(7.00,days,Double.valueOf(amount.getText()),maturity);
    			}
				check = true;
    			
    		}else {
    			JOptionPane.showMessageDialog(null,"Please Enter the Amount.");
    		}
    	}else {
    		JOptionPane.showMessageDialog(null, "Please select the \"Time Period\".");
    	}

    }
    
    
    public void settext(double rate, long days, double principal, String maturity) {
    	
    	//***************** set the text ounce user click proceed ****************
    	
    	this.rate.setText(String.valueOf(rate));
    	mdate.setText(maturity);

    	String str = DecimalFormat.getNumberInstance().format(interestCalculator.interest(principal,rate, days));
    	profit.setText(str);
    	
    }
    
    
    
    @FXML
    void submit(ActionEvent event) {
    	/*
    	 ********* First check whether user have the sufficient balance or not *******************
    	 *********************** then debit the amount ************************
    	 */
    	if(check) {
    		
        	conn = sqlconnect.dbconnect();
        	
        	Double balance;
    		
    		try {
    			stmt = conn.createStatement();
    			String bal = "select balance from "+data.getUsername()+data.getAccno();
    			rs = stmt.executeQuery(bal);
    			balance = rs.getDouble("balance");
    			if(balance >= Double.valueOf(amount.getText())){
    				/*
    				 ************** store the data in the fixed_deposit table
    				 **************  + debit from balance
    				 **************  + add trx to trx table
    				*/
    				ps = conn.prepareStatement("insert into FixedDeposit (accno,amount,fromD,toD, rate, interestaccum) values (?,?,?,?,?,?)");
    				ps.setInt(1, Integer.parseInt(data.getAccno()));
    				ps.setDouble(2, Double.valueOf(amount.getText()));
    				ps.setString(3,today);
    				ps.setString(4,maturity);
    				ps.setDouble(5, Double.valueOf(rate.getText()));
    				ps.setDouble(6, 0 );
    				ps.execute();
    				
    				conn.close();
    				
    				boolean flag = Debit.debit(Double.valueOf(amount.getText()), "Fixed Deposit of "+ amount.getText()+" with interest rate "+ rate.getText()+"%.");
    				if(flag == true) {
    					JOptionPane.showMessageDialog(null, "Transection Successfully.");
    					
    					try {
    						Connection conn = sqlconnect.dbconnect();
							Statement stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery("select * from user where accno = "+data.getAccno());
							String email = rs.getString("emailid");
							NumberFormat foramter = new DecimalFormat("#0.00");
							
							sendMail.sendmail("\t\tFIXED DEPOSIT CREATED\n"+"Fixed Deposit created of Rs."+amount.getText()+". With interest rate of "+rate.getText()+"%. And it's Maturity date is "+maturity+". And its expected profit is Rs."+foramter.format((RemoveComma.remove(profit.getText()) - RemoveComma.remove(amount.getText())))+".", email, "UPDATE");
							conn.close();
    					} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, "email id -->>"+e);
						}
    					
    					try {
    						
    						((Node)event.getSource()).getScene().getWindow().hide();
    						Stage primaryStage = new Stage();
    						FXMLLoader loader = new FXMLLoader();
    						Pane root = loader.load(getClass().getResource("/User/ActiveDeposit.fxml").openStream());
    						Scene scene = new Scene(root);
    						primaryStage.setScene(scene);
    						primaryStage.initStyle(StageStyle.TRANSPARENT);
    						primaryStage.show();
    						
    					} catch (Exception e) {
    						
    					}
    				}
    				
    			}else {
    				JOptionPane.showMessageDialog(null,"You Don't have a sufficient Balance.");
    			}
    			conn.close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}else {
    		JOptionPane.showMessageDialog(null, "Please Enter all the details before submiting");
    	}
    	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		accountnumber.setText(data.getAccno());
		username.setText(data.getUsername());
		
		
	}

}
