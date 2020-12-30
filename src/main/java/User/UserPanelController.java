package User;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.sqlconnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UserPanelController implements Initializable {
	
	@FXML 
	private Label lbl_name, lab_username;
	@FXML 
	private Label lab_accno;
	@FXML 
	private Label userbalance;
	@FXML 
	private Label lab_viewbalance;

	private double balance;
	
	Connection conn;
	ResultSet rs;
	Statement stmt;
	PreparedStatement ps;
				
	public void setName(String name) {
		lbl_name.setText(name);
		data.setName(name);
	}

	public void setUsername(String username) {
		lab_username.setText(username);
		data.setUsername(username);
	}

	public void setAccno(int accno) {
		lab_accno.setText(String.valueOf(accno));
		data.setAccno(String.valueOf(accno));
	}

			
	public void exxit(ActionEvent event) {
		System.exit(0);	
	}
	
	public void deposit(MouseEvent event) {
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
	
	public void loan(MouseEvent event) {
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
	}
	
	public void transfer(MouseEvent event) {
try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/User/MoneyTransfer.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "open" + e);
		}
		
	}
	
	public void transaction(MouseEvent event) {
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/User/RecentTransaction.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			
		} catch (Exception e) {
					
		}
		
	}
	
	public void feedback(MouseEvent event) {
		
try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/User/userComplaints.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			
		} catch (Exception e) {
			
		}
	}
			
	@FXML
	void view_balance(MouseEvent event) {
		if(event.getSource() == lab_viewbalance) {
			//***************** when user click on view balance ****************
			
			lab_viewbalance.setVisible(false);
			userbalance.setVisible(true);
			
			conn = sqlconnect.dbconnect();
			String sql = "select balance from "+data.getUsername()+data.getAccno();
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				balance = rs.getFloat("balance");
				userbalance.setText("Rs. "+DecimalFormat.getNumberInstance().format(balance)+"    (HIDE)");
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(event.getSource() == userbalance) {
			//****************** when user click on balance then balance will hide **************
			
			userbalance.setVisible(false);
			lab_viewbalance.setVisible(true);
		}
	}
			
	public void out(ActionEvent event) {	//***************** logout ***************************
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
			
	public void out1(ActionEvent event) {	//********************* go to recent trx. ************************
		try {
				
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/User/RecentTransaction.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			
		} catch (Exception e) {
					
		}
	}
			
	public void out2(ActionEvent event) {	//********************* go to online trx *************************
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/User/MoneyTransfer.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "open" + e);
		}
	}
			
	public void out3(ActionEvent event) {	//***************** go to loan section ****************
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
	}
			
	public void out4(ActionEvent event) {	//**************** go to feedback section *********************
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("/User/userComplaints.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			
		} catch (Exception e) {
			
		}
	}
	public void out5(ActionEvent event) {		//********************* go to fixed deposit section **********
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		userbalance.setVisible(false);
		lab_viewbalance.setVisible(true);
		lab_username.setVisible(false);
	}
}