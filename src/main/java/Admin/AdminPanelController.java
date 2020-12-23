package Admin;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminPanelController implements Initializable{


	@FXML private Label Adminlbl;
	
		
		public void SetAdminName(String admin) {
			
			Adminlbl.setText(admin);
			

		}
		
		public void exxit(ActionEvent event) {
				System.exit(0);	
				}
		
		public void out(ActionEvent event) { 	//******** logout
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
		
		public void out1(ActionEvent event) {		//************** withdraw
			try {
				
				((Node)event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/Admin/Withdraw.fxml").openStream());
				
				WithrawController withrawcomplaints = (WithrawController)loader.getController();
				withrawcomplaints.setName(Adminlbl.getText());
				
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.show();
				
			} catch (Exception e) {
				
			}
		}
		
		public void out2(ActionEvent event) {		///********** user
			try {
				
				((Node)event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/Admin/Users.fxml").openStream());
				
				UsersController userscontroller = (UsersController)loader.getController();
				userscontroller.setName(Adminlbl.getText());
				
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.show();
				
			} catch (Exception e) {
				
			}
		}
		
		public void out3(ActionEvent event) {		/// loan
			try {
				
				((Node)event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/Admin/loanapplied.fxml").openStream());
				
				LoansAppliedController loanapplied = (LoansAppliedController)loader.getController();
				loanapplied.setName(Adminlbl.getText());
				
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.show();
				
			} catch (Exception e) {
				
			}
		}
		
		public void out4(ActionEvent event) {		//********** admincomplaints
			try {
				
				((Node)event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/Admin/AdminComplaints.fxml").openStream());
				
				AdminComplaintsController admincomplaints = (AdminComplaintsController)loader.getController();
				admincomplaints.setName(Adminlbl.getText());
				
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.show();
				
			} catch (Exception e) {
				
			}
		}
       
		public void out5(ActionEvent event) {		//fixed deposits
			try {
				
				((Node)event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/Admin/FixedDeposits.fxml").openStream());
				
				FixedDepositController fixedcomplaints = (FixedDepositController)loader.getController();
				fixedcomplaints.setName(Adminlbl.getText());
				
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				primaryStage.show();
				
			} catch (Exception e) {
				
			}
		}
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
		}
		
	}
