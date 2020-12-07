package User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.Initializable;

public class userComplaintController implements Initializable{
	
	 @FXML
	    private AnchorPane verification;

	    @FXML
	    private ComboBox<String> combobox;

	    @FXML
	    private TextArea textarea;

	    @FXML
	    private Label accountnumber;

	    ObservableList<String> list = FXCollections.observableArrayList("Query", "Feedback","Complaint","Suggestion","Change some registered details");
	    @FXML
	    void dashboad(ActionEvent event) {
	    	try {
				
				((Node)event.getSource()).getScene().getWindow().hide();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/User/UserPanel.fxml").openStream());
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
	    	if(accountnumber.getText()!=null  &&  combobox.getValue()!=null  && textarea.getText()!=null)
	    	{
	    		JOptionPane.showMessageDialog(null, "Submitted");
	    	}
	    	

	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		accountnumber.setText(data.getAccno());
		combobox.setItems(list);
	}

}

  
