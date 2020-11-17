package Admin;

import java.net.URL;
import java.util.Random;
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

public class WithrawController implements Initializable {
	@FXML
	private ComboBox<String> combobox;
	@FXML
	private Label randomlbl;
	@FXML
    private TextField anumber;

    @FXML
    private TextField amount;

    @FXML
    private TextField uname;

    @FXML
    private TextField otp;

    @FXML
    private TextField captcha;

    @FXML
    private TextField remark;

	public void generateRandom(ActionEvent event) {
		Random rand = new Random();
		int myrand = rand.nextInt(100)+1;
		randomlbl.setText(Integer.toBinaryString(myrand));
	}
	
	
	ObservableList<String> list = FXCollections.observableArrayList("WITHDRAW", "DEPOSIT");
	public void exxit(ActionEvent event) {
		System.exit(0);	
		}

     public void out(ActionEvent event) {
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

    public void out1(ActionEvent event) {
	try {
		
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/Admin/AdminPanel.fxml").openStream());
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.show();
		
	} catch (Exception e) {
		
	}
}
    public void submit(ActionEvent event) {
    	if(uname.getText().trim().isEmpty() || anumber.getText().trim().isEmpty() || amount.getText().trim().isEmpty()
    		|| remark.getText().trim().isEmpty() || captcha.getText().trim().isEmpty()|| otp.getText().trim().isEmpty()
    		|| combobox.getValue().trim().isEmpty())
    	{
    		JOptionPane.showMessageDialog(null, "Enter all details properly");
			
		}else {
			
			JOptionPane.showMessageDialog(null, "Transaction is succesfull");
		}
		
		
	}
  

@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	// TODO Auto-generated method stub
	combobox.setItems(list);
}

}
