package User;

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

public class UserPanelController implements Initializable {
	
	    @FXML
		private Label userlbl;
	    @FXML
	    private Label userbalance;
	    

			
			public void GetAdmin(String user) {
				
				userlbl.setText(user);

		   }
			
			 public void exxit(ActionEvent event) {
					System.exit(0);	
					}
			
			public void out(ActionEvent event) {
				try {
					
					((Node)event.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/Frontend/Login.fxml").openStream());
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle(" Login ");
					primaryStage.show();
					
				} catch (Exception e) {
					
				}
			}
			
			public void out1(ActionEvent event) {
				try {
					
					((Node)event.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/Frontend/Depositwithraw.fxml").openStream());
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle(" New Payments ");
					primaryStage.show();
					
				} catch (Exception e) {
					
				}
			}
			
			public void out2(ActionEvent event) {
				try {
					
					((Node)event.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/Frontend/Users.fxml").openStream());
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle(" Pending Bills ");
					primaryStage.show();
					
				} catch (Exception e) {
					
				}
			}
			
			public void out3(ActionEvent event) {
				try {
					
					((Node)event.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/Frontend/loans.fxml").openStream());
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle(" New Bills ");
					primaryStage.show();
					
				} catch (Exception e) {
					
				}
			}
			
			public void out4(ActionEvent event) {
				try {
					
					((Node)event.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/Frontend/AdminComplaints.fxml").openStream());
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle(" Admin Complaints ");
					primaryStage.show();
					
				} catch (Exception e) {
					
				}
			}
			
			public void out5(ActionEvent event) {
				try {
					
					((Node)event.getSource()).getScene().getWindow().hide();
					Stage primaryStage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root = loader.load(getClass().getResource("/Frontend/Login.fxml").openStream());
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setTitle(" Login ");
					primaryStage.show();
					
				} catch (Exception e) {
					
				}
			}

			@Override
			public void initialize(URL arg0, ResourceBundle arg1) {
				// TODO Auto-generated method stub
				
			}
		}

