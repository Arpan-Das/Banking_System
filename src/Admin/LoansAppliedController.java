package Admin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.sendMail;
import application.sqlconnect;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import userDom.DueDate;
import userDom.RemoveComma;

public class LoansAppliedController implements Initializable {
	
	@FXML 
	private Label lab_name;
	public void setName(String name) {
		lab_name.setText(name);
	}
	
	@FXML
    private TableView<loansapplied> table_loans;

    @FXML
    private TableColumn<loansapplied, Integer> col_anumber;

    @FXML
    private TableColumn<loansapplied, String> col_username;
    
    @FXML
    private TableColumn<loansapplied, String> col_amount;

    @FXML
    private TableColumn<loansapplied, String> col_why;
    
    @FXML
    private TableColumn<loansapplied, String> col_status;
	
    @FXML
    private TextField txt_anumber;

    @FXML
    private TextField txt_username;
    
    @FXML
    private TextField txt_amount;

    @FXML
    private TextArea txt_remark;
    
    ObservableList<loansapplied> listM;
    

    int index = -1;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    Statement stmt;
    
    
    public void getSelected(MouseEvent event) {
    	index = table_loans.getSelectionModel().getSelectedIndex();
    	if(index <= -1) {
    		return;
    	}
    	txt_anumber.setText(col_anumber.getCellData(index).toString());
    	txt_username.setText(col_username.getCellData(index).toString());
    	txt_amount.setText(col_amount.getCellData(index).toString());
    	txt_remark.setText(col_status.getCellData(index).toString());

    }
    
    
    @FXML
    public void Reject(ActionEvent event) {
    	if(index != -1) {
        		if(col_status.getCellData(index).toString().equals("Pending")) {
        			        			
        			//-************ first email is send and then all the other stufs are done like - update the loan table 
        			String email = null,type = null;
        			
        			try {
        				conn = sqlconnect.dbconnect();
        				Statement stmt = conn.createStatement();
        				ResultSet rs = stmt.executeQuery("select * from user where accno = "+col_anumber.getCellData(index));
        				if(rs.next()) {
        					email = rs.getString("emailid");
        					
        				}
        				
        				
        				Statement st= conn.createStatement();
        				ResultSet rs2 = st.executeQuery("select * from loan where accno = "+col_anumber.getCellData(index)+" and amount = "+RemoveComma.remove(col_amount.getCellData(index)));
        				
        				if(rs2.next()) {
        					
        					type = rs2.getString("type");
        					System.out.println("***"+type+"**************-*******************");
        				}
        				
        				conn.close();
        			}catch(Exception e) {
        				JOptionPane.showMessageDialog(null, "error in email "+e);
        			}
        			
        			if(sendMail.sendmail("Dear Customer,\n\tYour Loan request for "+ type+" of amount Rs."+txt_amount.getText()+" has been REJECTED. Please contact Admin for further details.\nThank You\n\n\n****This is an system generated email please don't replay.", email, "Loan Update")) {
        				//*********** loan table is being update
        				try {
        					
        					conn = sqlconnect.dbconnect();
        					
            				ps = conn.prepareStatement("update loan set status = 'Reject' where accno = ? and amount = ? ");
                		    ps.setInt(1, col_anumber.getCellData(index));
                			ps.setDouble(2, RemoveComma.remove(col_amount.getCellData(index)));
                			ps.executeUpdate();
                			
        					conn.close();
        				}catch(Exception e) {
        					JOptionPane.showMessageDialog(null, e);
        				}
        				JOptionPane.showMessageDialog(null, "Rejected");
        				Update();
        					
        			}else {
        				JOptionPane.showMessageDialog(null,"No Internet Connection");
        			}
        			
    				txt_username.setText("");
    				txt_remark.setText("");
    				txt_anumber.setText("");
    				txt_amount.setText("");
    				
    				index = -1;
        		}else {
        			if(col_status.getCellData(index).equals("Approve")) {
        				JOptionPane.showMessageDialog(null, "Already Approved and Amount is already credited. Now you can't Reject.");
        			}else {
        				JOptionPane.showMessageDialog(null, "Already Rejected");
        			}
        		}
    			
    	}else {
    		JOptionPane.showMessageDialog(null, "Please select a Query.");
    	}
    	
    }
    
    public void Approve(ActionEvent event) {
    	
    	if(index != -1) { ///************* it will only execute if admin has selected any query
    		try {
        		if(!col_status.getCellData(index).toString().equals("Approve")) {
        			
        			/*
        			 * first mail is send and then rest of the stuf
        			 * like update the loan table 
        			 * then do the trx. in which you update the balance table and the trx. table and also credit the amount
        			 * */
        			
        			// current date 
        			LocalDate date = java.time.LocalDate.now();
        			String today  = date.getYear()+"-"+date.getMonthValue()+"-"+date.getDayOfMonth();
        			
        			DueDate duedate = new DueDate();
        			duedate.duedate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());	// calculate the due date
        			
        			String email = null,type =null;
        			double emi =0, rate =0, timeperiod =0 ;
        			
        			try {
        				conn = sqlconnect.dbconnect();
        				Statement stmt = conn.createStatement();
        				ResultSet rs = stmt.executeQuery("select * from user where accno = "+col_anumber.getCellData(index));
        				if(rs.next()) {
        					email = rs.getString("emailid");
        				}
        				ResultSet rs2 = stmt.executeQuery("select * from loan where accno = "+col_anumber.getCellData(index)+" and amount = "+RemoveComma.remove(col_amount.getCellData(index)));
        				if(rs2.next()) {
        					emi = rs2.getDouble("emi");
        					rate = rs2.getDouble("rate");
        					timeperiod = rs2.getDouble("timeperiod");
        					type = rs2.getString("type");
        				}
        				conn.close();
        			}catch(Exception e) {
        				JOptionPane.showMessageDialog(null, "error in email "+e);
        			}
        			
        			if(sendMail.sendmail("Dear Customer,\n\t Your Loan requested for "+type+" of Rs."+col_amount.getCellData(index)+" has been accepted on "+ today +".\n\t\tLoan Detail:\n\tLoan Type:"+col_why.getCellData(index)+"\n\tLoan Amount:Rs. "+col_amount.getCellData(index)+"\n\tInterest Rate:"+rate+"%\n\tTime Period:"+timeperiod+" year\n\tMonthly EMI: Rs."+emi+"\nYour amount will credit shortly.\nYour due date for emi is "+duedate.getDate()+"/"+duedate.getMonth()+"/"+duedate.getYear()+". And Please pay your monthly EMI in time for your convinient.\n\n\n**This is system generated e-mail please do not replay.", email, "Loan Update")) {
        				
        				//*************************** update approve in loan table -status to approve and due date 
        				conn = sqlconnect.dbconnect();
        				ps = conn.prepareStatement("update loan set status = 'Approve' , y = ? , m = ? , d = ?, dueamount = ? where accno = ? and amount = ? ");
            			ps.setInt(1, duedate.getYear());
            			ps.setInt(2, duedate.getMonth());
            			ps.setInt(3, duedate.getDate());
            			ps.setDouble(4, RemoveComma.remove(col_amount.getCellData(index)));
        				ps.setInt(5, col_anumber.getCellData(index));
            			ps.setDouble(6, RemoveComma.remove(col_amount.getCellData(index)));
            			ps.executeUpdate();
            			
            			JOptionPane.showMessageDialog(null, "Approve");
        			
            			//***************************** credit the loan ****************************
        			
            			// to get the previous balance
            			String query = "select * from "+ col_username.getCellData(index).toString()+col_anumber.getCellData(index);
            			stmt = conn.createStatement();
            			rs = stmt.executeQuery(query);
            			float balance = rs.getFloat("balance") ;
        					
            			// update the balance with added loan amount
            			stmt.execute("update "+col_username.getCellData(index).toString()+col_anumber.getCellData(index)+" set balance = " + (balance + RemoveComma.remove(col_amount.getCellData(index))));
        		
            			//********************** insert data into trx. table *******************************
        			
            			stmt.execute("create table IF NOT EXISTS temp (date text, remarks text, type text, amount real, balance real)");
        		
            			ps = conn.prepareStatement("insert into temp values(datetime('now','localtime'), ?, ?, ?,?)");
            			ps.setString(1, "Loan Amount");		// remark
            			ps.setString(2, "Credit");		// type credit/debit
            			ps.setDouble(3, RemoveComma.remove(col_amount.getCellData(index)));	// credit/debit amount
            			ps.setDouble(4, (balance + RemoveComma.remove(col_amount.getCellData(index))));	// final balance after the trx
            			ps.execute();
        		
            			stmt.execute("insert into temp select * from trx" +col_username.getCellData(index).toString()+col_anumber.getCellData(index) );
        		
            			stmt.execute("drop table trx" + col_username.getCellData(index).toString()+col_anumber.getCellData(index));
        		
            			stmt.execute("alter table temp rename to trx"+col_username.getCellData(index).toString()+col_anumber.getCellData(index));
        		
            			JOptionPane.showMessageDialog(null, "Amount Credited.");
        			
            			Update();
            			conn.close();
        			}else {
        				JOptionPane.showMessageDialog(null,"Please Check Your Internet Connection.");
        			}
        			
    			
        			txt_username.setText("");
        			txt_remark.setText("");
        			txt_anumber.setText("");
        			txt_amount.setText("");
    			
        			index = -1;
        			
        		}else {
        			JOptionPane.showMessageDialog(null, "Already Approved and All Trx. Complete.");
        		}
    			
    		} catch (SQLException e) {
    			
    			JOptionPane.showMessageDialog(null, e);
    		}
    	}else {
    		JOptionPane.showMessageDialog(null, "Please select a Query.");
    	}
    	
    }
    
    public void Update() {
    	col_anumber.setCellValueFactory(new PropertyValueFactory<loansapplied, Integer>("accno"));
		col_username.setCellValueFactory(new PropertyValueFactory<loansapplied, String>("username"));
		col_amount.setCellValueFactory(new PropertyValueFactory<loansapplied, String>("amount"));
		col_why.setCellValueFactory(new PropertyValueFactory<loansapplied, String>("why"));
		col_status.setCellValueFactory(new PropertyValueFactory<loansapplied, String>("status"));
		
		listM = sqlconnect.getDataloansapplied();
		table_loans.setItems(listM);
    }

	public void exxit(ActionEvent event) {
		System.exit(0);	
		}

    public void out(ActionEvent event) {		//************* logout
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

    public void out1(ActionEvent event) {		// *************  go back to admin panel
	try {
		
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/Admin/AdminPanel.fxml").openStream());

		AdminPanelController adminpanel = (AdminPanelController)loader.getController();
		adminpanel.SetAdminName(lab_name.getText());
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
		
	} catch (Exception e) {
		
	}
}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Update();
		lab_name.setVisible(false);
	}

}
