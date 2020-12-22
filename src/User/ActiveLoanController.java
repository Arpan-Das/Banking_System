package User;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import userDom.DueDate;
import userDom.RemoveComma;

public class ActiveLoanController implements Initializable{

    @FXML
    private TableView<activeloans> tabel_loan;

    @FXML
    private TableColumn<activeloans, String> col_amount;

    @FXML
    private TableColumn<activeloans, String> col_applieddate; // fromD

    @FXML
    private TableColumn<activeloans, String> col_type;

    @FXML
    private TableColumn<activeloans, String> col_emi;//emi

    @FXML
    private TableColumn<activeloans, Double> col_dueloan;
    
    @FXML
    private TableColumn<activeloans, String> col_remark;

    @FXML
    private TableColumn<activeloans, String> col_duedate;// hide-directly print on textfield

    @FXML
    private TableColumn<activeloans, String> col_status;// hide
    
    @FXML
    private TextField duedate;

    @FXML
    private TextField dueamount;// print emi
//
//    @FXML
//    private Label accountnumber;
//
//    @FXML
//    private Label username;
//
//    @FXML
//    private Label name;

    @FXML
    private Label adminaccountnumber;                           // 1000145

    ObservableList<activeloans> listloans;
    
    int index = -1;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    Statement stmt;
    boolean select = false;
    String loantype ;// to set the remark  while user go for emi payment
    String loanamount;// to set the remark  while user go for emi payment
    @FXML
    void exxit(ActionEvent event) {

    	System.exit(0);
    }

    @FXML
    void getSelected(MouseEvent event) {
    	try {
    	index = tabel_loan.getSelectionModel().getSelectedIndex();
    	if(index <= -1) {
    		return;
    	}
    	
    	loantype = col_type.getCellData(index).toString();
    	loanamount = col_amount.getCellData(index).toString();
    	
    	String status = col_status.getCellData(index).toString();
    	
    	if(!(status.equals("Pending")||status.equals("Reject"))) {
    		dueamount.setText(col_emi.getCellData(index).toString());
        	duedate.setText(col_duedate.getCellData(index).toString());
        	
        	select = true;
    	}else {
    		if(status.equals("Pending")) {
    			JOptionPane.showMessageDialog(null, "Loan is not approved yet. You will get a email alert when loan gets approved.");
    		}else {
    			JOptionPane.showMessageDialog(null, "Your "+loantype+" request of Rs."+loanamount+" has been rejected.");
    		}
    		
    	}
    	
    	
    	index = -1;
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, e);
    	}
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
    void neww(ActionEvent event) {	//************* go to apply for new loan section -- loan.fxml + loanController.java
    	try {
    		
    		((Node)event.getSource()).getScene().getWindow().hide();
    		Stage primaryStage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/User/Loan.fxml").openStream());
    		Scene scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.initStyle(StageStyle.TRANSPARENT);
    		primaryStage.show();
    		
    	} catch (Exception e) {
    		
    	}
    	
    }

    @FXML
    void dashboard(ActionEvent event) {
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
    void pay(ActionEvent event) {//************ pay the monthly emi *****************
    	if(select) {
    		
    		try {
    			data.setLoanflag(true);
    			data.setLoanamount(RemoveComma.remove(loanamount));
        		((Node)event.getSource()).getScene().getWindow().hide();
        		Stage primaryStage = new Stage();
        		FXMLLoader loader = new FXMLLoader();
        		Pane root = loader.load(getClass().getResource("/User/MoneyTransfer.fxml").openStream());
        		MoneyTransferController moneyController = (MoneyTransferController)loader.getController();
    			moneyController.setloan(adminaccountnumber.getText(), dueamount.getText(), "EMI for "+ loantype+" of Rs."+loanamount);
        		Scene scene = new Scene(root);
        		primaryStage.setScene(scene);
        		primaryStage.initStyle(StageStyle.TRANSPARENT);
        		primaryStage.show();
        		
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
    	}else {
    		JOptionPane.showMessageDialog(null,"Please select a Query.");
    	}
    	
    	
    }
    
    public void Update() {
    	try {
    	col_amount.setCellValueFactory(new PropertyValueFactory<activeloans, String>("amount"));
		col_type.setCellValueFactory(new PropertyValueFactory<activeloans, String>("type"));
		col_remark.setCellValueFactory(new PropertyValueFactory<activeloans, String>("remark")); 
		col_duedate.setCellValueFactory(new PropertyValueFactory<activeloans, String>("duedate"));
		col_applieddate.setCellValueFactory(new PropertyValueFactory<activeloans, String>("applieddate"));
		col_emi.setCellValueFactory(new PropertyValueFactory<activeloans, String>("emi"));
		col_dueloan.setCellValueFactory(new PropertyValueFactory<activeloans, Double>("dueloan"));
		col_status.setCellValueFactory(new PropertyValueFactory<activeloans, String>("status"));
		
		listloans = sqlconnect.getDataloans(Integer.parseInt(data.getAccno()));
		tabel_loan.setItems(listloans);
    	}catch(Exception e) {
    		e.printStackTrace();
    		JOptionPane.showMessageDialog(null, e);
    	}
    }
		

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		if(data.getTrxflag()) {
			try {
    			//******************* update the duedate and due amount
    			Connection conn = sqlconnect.dbconnect();
    			int y=0 , m=0 , d=0;// due date
    			double emi = 0 , dueamount = 0;
    			Statement stmt = conn.createStatement();
    			ResultSet rs = stmt.executeQuery("select * from loan where accno = "+data.getAccno()+" and amount = "+data.getLoanamount());
    			if(rs.next()) {
    				y=rs.getInt("y");
    				m = rs.getInt("m");
    				d = rs.getInt("d");
    				emi = rs.getDouble("emi");
    				dueamount= rs.getDouble("dueamount");
    			}
    			DueDate duedate = new DueDate();
    			duedate.duedate(y, m, d);
    			Statement stmt2 = conn.createStatement();
    			stmt2.execute("update loan set dueamount = "+(dueamount-emi)+", y = "+duedate.getYear()+" , m = "+duedate.getMonth()+" , d = "+duedate.getDate()+" where accno = "+data.getAccno()+" and amount = "+data.getLoanamount());
    			conn.close();
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
			data.setLoanflag(false);
			data.setTrxflag(false);
		}
		Update();
		adminaccountnumber.setText("1014");
	}

}
