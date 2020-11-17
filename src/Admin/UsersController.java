package Admin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class UsersController implements Initializable  {

	@FXML
	private TableView<user> table_user;

	@FXML
	private TableColumn<user, Integer> col_id;

	@FXML
	private TableColumn<user, String> col_username;

	@FXML
    private TableColumn<user, String> col_gender;

	@FXML
    private TableColumn<user, String> col_email;

    @FXML
    private TableColumn<user, String> col_name;
    
    @FXML
	private TableColumn<user, Integer> col_anumber;
    @FXML
	private TableColumn<user, Integer> col_mnumber;

    
    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_gender;
    
    @FXML
    private TextField txt_name;
    
    @FXML
    private TextField txt_anumber;
    
    @FXML
    private TextField txt_mnumber;
    
    ObservableList<user> listM;
    
    int index = -1;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    
    public void Add_user() {
    	conn = sqlconnect.dbconnect();
    	try {
			ps = conn.prepareStatement("insert into user (accountnumber, name, gender, id, username, email, mobilenumber) values(?,?,?,?,?,?,?)");
			ps.setString(1,txt_anumber.getText());
			ps.setString(2, txt_name.getText());
			ps.setString(3, txt_gender.getText());
			ps.setString(4, txt_id.getText());
			ps.setString(5, txt_username.getText());
			ps.setString(6, txt_email.getText());
			ps.setString(7, txt_mnumber.getText());
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Successfully inserted");
			
			Update();
			
			txt_id.setText("");
			txt_username.setText("");
			txt_gender.setText("");
			txt_email.setText("");
			txt_name.setText("");
			txt_mnumber.setText("");
			txt_anumber.setText("");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
    }
    
    public void getSelected(MouseEvent event) {
    	index = table_user.getSelectionModel().getSelectedIndex();
    	if(index <= -1) {
    		return;
    	}
    	txt_id.setText(col_id.getCellData(index).toString());
    	txt_username.setText(col_username.getCellData(index).toString());
    	txt_gender.setText(col_gender.getCellData(index).toString());
    	txt_email.setText(col_email.getCellData(index).toString());
    	txt_name.setText(col_name.getCellData(index).toString());
    	txt_anumber.setText(col_anumber.getCellData(index).toString());
    	txt_mnumber.setText(col_mnumber.getCellData(index).toString());
    	
    }
    
    public void Edit(ActionEvent event) {
    	try {
    		conn = sqlconnect.dbconnect();
    		String id = txt_id.getText();
    		String username = txt_username.getText();
    		String gender = txt_gender.getText();
    		String email = txt_email.getText();
    		String name = txt_name.getText();
    		String anumber = txt_anumber.getText();
    		String mnumber = txt_mnumber.getText();
    		
    		String sql = "update user set accountnumber = "+ anumber +", username = '"+ username +"', gender = '"+ gender 
    				+"', email = '"+ email +"', name = '"+ name +", mnumber = '"+ mnumber +", id = '"+ id +"' where  anumber = "+ anumber+" ";
    		ps = conn.prepareStatement(sql);
    		ps.execute();
    		
    		JOptionPane.showMessageDialog(null, "Successfully Updated");
    		
    		Update();
    		
    		txt_id.setText("");
			txt_username.setText("");
			txt_gender.setText("");
			txt_email.setText("");
			txt_name.setText("");
			txt_mnumber.setText("");
			txt_anumber.setText("");
    		
			
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, e);
    	}
    }
    
    @FXML
    public void Delete_user(ActionEvent event) {
    	conn = sqlconnect.dbconnect();
    	
    	try {
    		
			ps = conn.prepareStatement("delete from user where acountnumber = ?");
			ps.setInt(1, col_anumber.getCellData(index));			
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "DeletedSuccessfully");
			
			Update();
			
			txt_id.setText("");
			txt_username.setText("");
			txt_gender.setText("");
			txt_email.setText("");
			txt_name.setText("");
			txt_mnumber.setText("");
			txt_anumber.setText("");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
    }
    
    public void Update() {
    	col_id.setCellValueFactory(new PropertyValueFactory<user, Integer>("id"));
		col_username.setCellValueFactory(new PropertyValueFactory<user, String>("username"));
		col_gender.setCellValueFactory(new PropertyValueFactory<user, String>("gender"));
		col_email.setCellValueFactory(new PropertyValueFactory<user, String>("email"));
		col_name.setCellValueFactory(new PropertyValueFactory<user, String>("name"));
		col_anumber.setCellValueFactory(new PropertyValueFactory<user, Integer>("accountnumber"));
		col_mnumber.setCellValueFactory(new PropertyValueFactory<user, Integer>("mobilenumber"));
		
		listM = sqlconnect.getDatauser();
		table_user.setItems(listM);
    }
    
    
    
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Update();
		
	}
}
