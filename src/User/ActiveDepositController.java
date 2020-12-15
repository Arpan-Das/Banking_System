package User;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import userDom.Credit;
import userDom.dateCalculator;
import userDom.interestCalculator;

public class ActiveDepositController implements Initializable {

    @FXML
    private TableView<deposit> tabel_fixs;

    @FXML
    private TableColumn<deposit, Double> col_amount;

    @FXML
    private TableColumn<deposit, String> col_applieddate;

    @FXML
    private TableColumn<deposit, Double> col_rate;

    @FXML
    private TableColumn<deposit, Double> col_interest;

    @FXML
    private TableColumn<deposit, String> col_maturitydate;

    @FXML
    private TextField amount;

    @FXML
    private TextField interest;
    
    ObservableList<deposit> listloans;
    

    int index = -1;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    Statement stmt;

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
    void exxit(ActionEvent event) {

    	System.exit(0);
    }

    @FXML
    void getSelected(MouseEvent event) {
    	index = tabel_fixs.getSelectionModel().getSelectedIndex();
    	if(index <= -1) {
    		return;
    	}
    	amount.setText(col_amount.getCellData(index).toString());
    	interest.setText(col_interest.getCellData(index).toString());
    	
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
    void neww(ActionEvent event) {

      try {
    		
    		((Node)event.getSource()).getScene().getWindow().hide();
    		Stage primaryStage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/User/FixedDeposit.fxml").openStream());
    		Scene scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.initStyle(StageStyle.TRANSPARENT);
    		primaryStage.show();
    		
    	} catch (Exception e) {
    		
    	}
    	
    }

    @FXML
    void breakk(ActionEvent event) {
    	/*
    	 * ************ 1) credit to user account
    	 * ************ 2) add trx to trx table
    	 * ************ 3) delete the deposit from the FixedDeposit table
    	*/
    	if(index != -1) {
    		//**** current date
    		LocalDate date = java.time.LocalDate.now();
			String today  = date.getYear()+"-"+date.getMonthValue()+"-"+date.getDayOfMonth();
			// do the trx. thing
			double amountcredit =interestCalculator.interest(col_amount.getCellData(index).doubleValue(), col_rate.getCellData(index).doubleValue(), dateCalculator.days(today, col_applieddate.getCellData(index).toString()));
    		boolean flag = Credit.credit(amountcredit, "Breaking Fixed Deposit of "+ amount.getText());
    		
    		//###3#### note later on you have to compare the current date and the maturity date so that you can break the deposit automatically
    		
    		if(flag) {
    			
    			try {
    				Connection conn = sqlconnect.dbconnect();
        			Statement stmt = conn.createStatement();
					stmt.execute("delete from FixedDeposit where accno = " + data.getAccno() + " and amount = " + col_amount.getCellData(index).doubleValue());
					conn.close();
    			} catch (SQLException e) {
					
					e.printStackTrace();
				}
    			
    			JOptionPane.showMessageDialog(null, "Transection Successfull.");
    			try {
    				Connection conn = sqlconnect.dbconnect();
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from user where accno = "+data.getAccno());
					String email = rs.getString("emailid");
					sendMail.sendmail("\t\tALART!!-FIXED DEPOSIT BREAKED\n"+"Fixed Deposit breaked of Rs."+col_amount.getCellData(index).doubleValue()+". With interest rate of "+col_rate.getCellData(index).doubleValue()+"%. \n\n Total amount of Rs."+amountcredit+" credited in your acount." , email, "UPDATE");
					conn.close();
    			} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, "email id -->>"+e);
				}
    			Update();
    			amount.setText("");
    			interest.setText("");
    		}
    		index = -1;
    	}else {
    		JOptionPane.showMessageDialog(null, "Please Select a Querry");
    	}
    	
   }
    
    public void Update() {
    	col_amount.setCellValueFactory(new PropertyValueFactory<deposit, Double>("amount"));
		col_rate.setCellValueFactory(new PropertyValueFactory<deposit, Double>("rate"));
		col_applieddate.setCellValueFactory(new PropertyValueFactory<deposit, String>("applieddate"));
		col_interest.setCellValueFactory(new PropertyValueFactory<deposit, Double>("profit"));
		col_maturitydate.setCellValueFactory(new PropertyValueFactory<deposit, String>("mdate"));
		
		listloans = sqlconnect.getDatadeposits(Integer.parseInt(data.getAccno()));
		tabel_fixs.setItems(listloans);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		/*
		 * *********to update the interestaccumulated
		 * */
		
		try {
			
			conn = sqlconnect.dbconnect();
			stmt = conn.createStatement();
			Statement stmt2=conn.createStatement();
			rs = stmt.executeQuery("select * from FixedDeposit where accno = "+data.getAccno());
			while(rs.next()){
				double amount = rs.getDouble("amount");
				double rate = rs.getDouble("rate");
				String fromD= rs.getString("fromD");
				
				LocalDate date = java.time.LocalDate.now();
    			String ttoday  = date.getYear()+"-"+date.getMonthValue()+"-"+date.getDayOfMonth();
				
    			double interest = interestCalculator.interest(amount, rate, dateCalculator.days(fromD, ttoday)) - amount ;
    			
    			try {
    				stmt2.executeUpdate("update FixedDeposit set interestaccum = "+ interest +" where amount = "+ amount+" and rate = "+rate);
    	    		
    			}catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
			conn.close();
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
				
		Update();
	}

}
