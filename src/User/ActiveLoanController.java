package User;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

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

public class ActiveLoanController implements Initializable{

    @FXML
    private TableView<activeloans> tabel_loan;

    @FXML
    private TableColumn<activeloans, Double> col_amount;

    @FXML
    private TableColumn<activeloans, String> col_applieddate;

    @FXML
    private TableColumn<activeloans, String> col_type;

    @FXML
    private TableColumn<activeloans, Double> col_pay;

    @FXML
    private TableColumn<activeloans, Double> col_paid;

    @FXML
    private TableColumn<activeloans, String> col_duedate;

    @FXML
    private TableColumn<activeloans, Double> col_dueamount;

    @FXML
    private TableColumn<activeloans, String> col_remark;

    @FXML
    private TextField duedate;

    @FXML
    private Label accountnumber;

    @FXML
    private Label username;

    @FXML
    private Label name;

    @FXML
    private TextField dueamount;
    
    @FXML
    private Label adminaccountnumber;                           // 1000145

    
    ObservableList<activeloans> listloans;
    

    int index = -1;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    Statement stmt;

    @FXML
    void exxit(ActionEvent event) {

    	System.exit(0);
    }

    @FXML
    void getSelected(MouseEvent event) {
    	index = tabel_loan.getSelectionModel().getSelectedIndex();
    	if(index <= -1) {
    		return;
    	}
    	dueamount.setText(col_dueamount.getCellData(index).toString());
    	duedate.setText(col_duedate.getCellData(index).toString());

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
    void pay(ActionEvent event) {
    	try {
    		
    		((Node)event.getSource()).getScene().getWindow().hide();
    		Stage primaryStage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/User/MoneyTransfer.fxml").openStream());
    		MoneyTransferController moneyController = (MoneyTransferController)loader.getController();
			moneyController.setloan(adminaccountnumber.getText(), dueamount.getText());
    		Scene scene = new Scene(root);
    		primaryStage.setScene(scene);
    		primaryStage.initStyle(StageStyle.TRANSPARENT);
    		primaryStage.show();
    		
    	} catch (Exception e) {
    		
    	}
    	
    }
    
    public void Update() {
    	col_amount.setCellValueFactory(new PropertyValueFactory<activeloans, Double>("amount"));
		col_type.setCellValueFactory(new PropertyValueFactory<activeloans, String>("type"));
		col_remark.setCellValueFactory(new PropertyValueFactory<activeloans, String>("remark")); 
		col_duedate.setCellValueFactory(new PropertyValueFactory<activeloans, String>("duedate"));
		col_dueamount.setCellValueFactory(new PropertyValueFactory<activeloans, Double>("duebalance"));
		col_applieddate.setCellValueFactory(new PropertyValueFactory<activeloans, String>("applieddate"));
		col_pay.setCellValueFactory(new PropertyValueFactory<activeloans, Double>("pay"));
		col_paid.setCellValueFactory(new PropertyValueFactory<activeloans, Double>("paid"));
		
		listloans = sqlconnect.getDataloans(data.getAccno());
		tabel_loan.setItems(listloans);
    }
		

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Update();
	}

}
