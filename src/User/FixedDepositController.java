package User;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FixedDepositController implements Initializable{

    @FXML
    private TextField accountnumber;

    @FXML
    private TextField username;

    @FXML
    private TextField amount;

    @FXML
    private ComboBox<String> combobox;

    @FXML
    private TextField mdate;

    @FXML
    private TextField profit;

    @FXML
    private TextField rate;
    
    @FXML
    private Label adminaccountnumber;
    
    ObservableList<String> list = FXCollections.observableArrayList("Home Loan", "Car Loan","Study Loan","Bussiness Loan","Medical Loan","Personal Loan");

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
    void proceed(ActionEvent event) {
    	if(amount.getText().trim().isEmpty())
    	{
    		JOptionPane.showMessageDialog(null, "Fill amount first");
    	}
    	if(combobox.getValue()=="1-5 years" && amount.getText()!=null)
    	{
    		mdate.setText("12-03-2001");
    		rate.setText("6%");
    		profit.setText("Rs. 12003");
    	}

    	
    }

    @FXML
    void submit(ActionEvent event) {
     try {
    		
    		((Node)event.getSource()).getScene().getWindow().hide();
    		Stage primaryStage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/User/MoneyTransfer.fxml").openStream());
    		MoneyTransferController moneyController = (MoneyTransferController)loader.getController();
			moneyController.setloan(adminaccountnumber.getText(), amount.getText());
    		Scene scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.initStyle(StageStyle.TRANSPARENT);
    		primaryStage.show();
    		
    	} catch (Exception e) {
    		
    	}

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		accountnumber.setText(data.getAccno());
		username.setText(data.getUsername());
		combobox.setItems(list);
	}

}
