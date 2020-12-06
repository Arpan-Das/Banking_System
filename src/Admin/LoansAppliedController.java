package Admin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

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
    private TableColumn<loansapplied, Float> col_amount;

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
    	
    	
    	try {
    		if(!col_status.getCellData(index).toString().equals("Approve")) {
    			conn = sqlconnect.dbconnect();
    			ps = conn.prepareStatement("update loan set status = 'Reject' where accno = ? and amount = ? ");
    			ps.setInt(1, col_anumber.getCellData(index));
				ps.setFloat(2, col_amount.getCellData(index));
				ps.executeUpdate();
			
				JOptionPane.showMessageDialog(null, "Rejected");
			
				Update();
			
				txt_username.setText("");
				txt_remark.setText("");
				txt_anumber.setText("");
				txt_amount.setText("");
				index = -1;
    		}else {
    			JOptionPane.showMessageDialog(null, "Already Approved and Amount is already credited. Now you can't Reject.");
    		}
			conn.close();
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, e);
		}
    }
    
    public void Approve(ActionEvent event) {
    	
    	
    	try {
    		if(!col_status.getCellData(index).toString().equals("Approve")) {
    			conn = sqlconnect.dbconnect();
    			ps = conn.prepareStatement("update loan set status = 'Approve' where accno = ? and amount = ? ");
    			ps.setInt(1, col_anumber.getCellData(index));
    			ps.setFloat(2, Float.valueOf(col_amount.getCellData(index)));
    			ps.executeUpdate();
			
    			JOptionPane.showMessageDialog(null, "Approve");
			
    			//***************************** credit the loan
			
    			// to get the previous balance
    			String query = "select * from "+ col_username.getCellData(index).toString()+col_anumber.getCellData(index);
    			stmt = conn.createStatement();
    			rs = stmt.executeQuery(query);
    			float balance = rs.getFloat("balance") ;
					
    			// update the balance with added loan amount
    			stmt.execute("update "+col_username.getCellData(index).toString()+col_anumber.getCellData(index)+" set balance = " + (balance + Float.valueOf(col_amount.getCellData(index))));
		
    			//********************** insert data into trx. table *******************************
			
    			stmt.execute("create table IF NOT EXISTS temp (date text, remarks text, type text, amount real, balance real)");
		
    			ps = conn.prepareStatement("insert into temp values(datetime('now','localtime'), ?, ?, ?,?)");
    			ps.setString(1, "Loan Amount");		// remark
    			ps.setString(2, "Credit");		// type credit/debit
    			ps.setFloat(3, Float.valueOf(col_amount.getCellData(index)));	// credit/debit amount
    			ps.setFloat(4, (balance + Float.valueOf(col_amount.getCellData(index))));	// final balance after the trx
    			ps.execute();
		
    			stmt.execute("insert into temp select * from trx" +col_username.getCellData(index).toString()+col_anumber.getCellData(index) );
		
    			stmt.execute("drop table trx" + col_username.getCellData(index).toString()+col_anumber.getCellData(index));
		
    			stmt.execute("alter table temp rename to trx"+col_username.getCellData(index).toString()+col_anumber.getCellData(index));
		
    			JOptionPane.showMessageDialog(null, "Amount Credited.");
			
    			Update();
			
    			txt_username.setText("");
    			txt_remark.setText("");
    			txt_anumber.setText("");
    			txt_amount.setText("");
			
    			index = -1;
    		}else {
    			JOptionPane.showMessageDialog(null, "Already Approved and All Trx. Complete.");
    		}
			conn.close();
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, e);
		}
    }
    
    public void Update() {
    	col_anumber.setCellValueFactory(new PropertyValueFactory<loansapplied, Integer>("accno"));
		col_username.setCellValueFactory(new PropertyValueFactory<loansapplied, String>("username"));
		col_amount.setCellValueFactory(new PropertyValueFactory<loansapplied, Float>("amount"));
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
