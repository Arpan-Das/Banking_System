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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RecenTransactionController implements Initializable {
	

	    @FXML
	    private TableView<activitylog> tabel_log;

	    @FXML
	    private TableColumn<activitylog, Double> col_amount;

	    @FXML
	    private TableColumn<activitylog, String> col_date;

	    @FXML
	    private TableColumn<activitylog, String> col_type;

	    @FXML
	    private TableColumn<activitylog, String> col_remark;

	    @FXML
	    private TableColumn<activitylog, String> col_balance;

	    @FXML
	    private TextField amount;

	    @FXML
	    private TextField date;

	    @FXML
	    private TextField type;

	    @FXML
	    private TextArea remark;

	    @FXML
	    private Label accountnumber;
	    @FXML
	    private Label name;
	    
	    
//	    public void setName(String username,String accountnumber, String name) {
//	    	this.username.setText(username);
//	    	this.accountnumber.setText(accountnumber);
//	    	this.name.setText(name);
//	    }

	    ObservableList<activitylog> listlog;
	    

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
	    	index = tabel_log.getSelectionModel().getSelectedIndex();
	    	if(index <= -1) {
	    		return;
	    	}
	    	amount.setText(col_amount.getCellData(index).toString());
	    	type.setText(col_type.getCellData(index).toString());
	    	remark.setText(col_remark.getCellData(index).toString());
	    	date.setText(col_date.getCellData(index).toString());

	    }

	    @FXML
	    void out(ActionEvent event) {	//*************** log out ********************************

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
	    
	    public void Update() {
	    	col_amount.setCellValueFactory(new PropertyValueFactory<activitylog, Double>("amount"));
			col_type.setCellValueFactory(new PropertyValueFactory<activitylog, String>("type"));
			col_remark.setCellValueFactory(new PropertyValueFactory<activitylog, String>("remark")); 
			col_date.setCellValueFactory(new PropertyValueFactory<activitylog, String>("date"));
			col_balance.setCellValueFactory(new PropertyValueFactory<activitylog, String>("balance"));
			
//			JOptionPane.showMessageDialog(null, username.getText() +"----"+ accountnumber.getText());
//			JOptionPane.showMessageDialog(null, data.getUsername()+"----"+data.getAccno());
			
			listlog = sqlconnect.getDatalog(data.getUsername(),data.getAccno());
			tabel_log.setItems(listlog);
	    }

	    @FXML
	    void out1(ActionEvent event) {		//**************** go back to user panel ***********************
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

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			Update();
			name.setText(data.getName());
			accountnumber.setText(data.getAccno());
			
			
		}

	}


