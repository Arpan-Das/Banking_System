package User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import userDom.DueDate;
import userDom.EMI;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.sendMail;
import application.sqlconnect;
import javafx.fxml.Initializable;

public class LoanController implements Initializable{
	
//	    @FXML
//	    private AnchorPane verification;
	    @FXML
	    private ComboBox<String> combobox;
	    @FXML 
	    private ComboBox<Integer> timeperiod;
	    @FXML
	    private TextArea textarea;
	    @FXML
	    private TextField text_emi, text_rate;
	    @FXML
	    private TextField amount;
	    
	    private boolean flag = false;
	    private double emi = 0.0;

	    ObservableList<String> list = FXCollections.observableArrayList("Home Loan", "Car Loan","Study Loan","Bussiness Loan","Medical Loan","Personal Loan");
	    ObservableList<Integer> year = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
	    
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

	    @FXML
	    void submit(ActionEvent event) {
	    	/*
	    	 * ************* to insert the data in the loan table and 
	    	 * ************* send a email regarding loan ***********************************
	    	 * */
	    	if(flag)
	    	{
	    		LocalDate date = java.time.LocalDate.now();
	    		String today  = date.getYear()+"-"+date.getMonthValue()+"-"+date.getDayOfMonth();
	    		DueDate duedate = new DueDate();
	    		duedate.duedate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
	    		NumberFormat format = new DecimalFormat("#0.00");
	    		try {
	    			Connection conn = sqlconnect.dbconnect();
	    			PreparedStatement ps = conn.prepareStatement("insert into loan (accno, username, amount, rate, timeperiod, type, fromD, dueamount, emi, y,m,d, remark) values (?,?,?,?,?,?,?,?,?,?,?,?,?) ");
	    			ps.setString(1, data.getAccno());
	    			ps.setString(2, data.getUsername());
	    			ps.setDouble(3, Double.parseDouble(amount.getText()));
	    			ps.setDouble(4, Double.parseDouble(text_rate.getText()));
	    			ps.setDouble(5, timeperiod.getValue());
	    			ps.setString(6, combobox.getValue());
	    			ps.setString(7, today);
	    			ps.setDouble(8, 0.0);
	    			ps.setString(9, format.format(emi));
	    			ps.setInt(10, duedate.getYear());
	    			ps.setInt(11, duedate.getMonth());
	    			ps.setInt(12, duedate.getDate());
	    			ps.setString(13, textarea.getText());
	    			ps.execute();
	    			try {
	    				Statement stmt = conn.createStatement();
	    				ResultSet rs = stmt.executeQuery("select * from user where accno = "+ data.getAccno());
	    				String email;
	    				if(rs.next()) {
	    					email = rs.getString("emailid");
	    					sendMail.sendmail("Dear Customer,\n\tYou have applied for a "+combobox.getValue()+" of amount Rs."+amount.getText()+". Your rate of Interest is "+ text_rate.getText()+"% p.a.. \nAs per your detail your have to pay a Monthly EMI of Rs."+text_emi.getText()+" for "+timeperiod.getValue()+" years. \n\n\nThankYou\n\n\n**You will get a Email alert when your loan is approved.\nThis is system generated Email - Please Do not replay.", email, "Loan Applied Alert!!");
	    					
	    				}else{
	    					JOptionPane.showMessageDialog(null, "email id error");
	    				}
	    				
	    			}catch(Exception e) {
	    				
	    			}
	    			JOptionPane.showMessageDialog(null, "Successfully Submited");
	    			
	    			//*************** go back to active loans
	    			 try {
	    					
	    					((Node)event.getSource()).getScene().getWindow().hide();
	    					Stage primaryStage = new Stage();
	    					FXMLLoader loader = new FXMLLoader();
	    					Pane root = loader.load(getClass().getResource("/User/ActiveLoans.fxml").openStream());
	    					Scene scene = new Scene(root);
	    					primaryStage.setScene(scene);
	    					primaryStage.initStyle(StageStyle.TRANSPARENT);
	    					primaryStage.show();
	    					
	    				} catch (Exception e) {
	    					JOptionPane.showMessageDialog(null, e);
	    				}
	    			conn.close();
	    		}catch(Exception e) {
	    			JOptionPane.showMessageDialog(null, e);
	    		}
	    		
	    	}else {
	    		JOptionPane.showMessageDialog(null, "Check Your  Monthly EMI, Before Submiting.");
	    	}
	    	

	    }
	    
	    @FXML
	    void CalculateEMI() {
	    	/*
	    	 * ***************************** to calculate  EMI *************************
	    	*/
	    	if(combobox.getValue() != null && amount.getText() != null && timeperiod.getValue() != null) {
	    		/*
	    		 *  Home Loans - 6.12%
					Car Loans - 10.74%
					Study Loans - 2.5%
					Medical Loans- 5.89%
					Business Loans - 11.96%
					Personal Loan 12.5%
	    		 */
	    		if(combobox.getValue().equals("Home Loan")) {
	    			//******* for home loans 
	    			text_rate.setText("6.12");
	    		}else if(combobox.getValue().equals("Car Loan")) {
	    			//******* for home loans 
	    			text_rate.setText("10.74");
	    		}else if(combobox.getValue().equals("Study Loan")) {
	    			//******* for home loans 
	    			text_rate.setText("2.5");
	    		}else if(combobox.getValue().equals("Medical Loan")) {
	    			//******* for home loans 
	    			text_rate.setText("5.89");
	    		}else if(combobox.getValue().equals("Bussiness Loan")) {
	    			//******* for home loans 
	    			text_rate.setText("11.96");
	    		}else if(combobox.getValue().equals("Personal Loan")) {
	    			//******* for home loans 
	    			text_rate.setText("12.50");
	    		}
	    		
	    		
	    		NumberFormat format = new DecimalFormat("#0.00");
	    		emi = EMI.emi(Double.parseDouble(amount.getText()), Double.parseDouble(text_rate.getText()), Double.valueOf(timeperiod.getValue()));
	    		text_emi.setText(format.format(emi));
	    		
	    		flag = true;
	    		
	    	}else {
	    		JOptionPane.showMessageDialog(null,"Please Fill all the details.");
	    	}
	    	
	    	
	    	
	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		combobox.setItems(list);
		timeperiod.setItems(year);
	}

}

  
