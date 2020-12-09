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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    	
    	JOptionPane.showMessageDialog(null, "You can break it");

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
		Update();
	}

}
