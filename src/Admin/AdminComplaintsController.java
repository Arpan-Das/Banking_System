package Admin;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminComplaintsController implements Initializable {
	
	@FXML
    private TableView<complaints> table_complaints;

    @FXML
    private TableColumn<complaints, Integer> col_anumber;

    @FXML
    private TableColumn<complaints, String> col_type;

    @FXML
    private TableColumn<complaints, String> col_remark;
	
    @FXML
    private TextField txt_anumber;

    @FXML
    private TextField txt_type;

    @FXML
    private TextArea txt_remark;
    
    ObservableList<complaints> listM;
    

    int index = -1;
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    Statement stmt;
    
    
    public void getSelected(MouseEvent event) {
    	index = table_complaints.getSelectionModel().getSelectedIndex();
    	if(index <= -1) {
    		return;
    	}
    	txt_anumber.setText(col_anumber.getCellData(index).toString());
    	txt_type.setText(col_type.getCellData(index).toString());
    	txt_remark.setText(col_remark.getCellData(index).toString());

    }
    
    
    @FXML
    public void Delete_complaint(ActionEvent event) {
    	conn = sqlconnect.dbconnect();
    	
    	try {
    		
			ps = conn.prepareStatement("delete from user where accno = ?");
			ps.setInt(1, col_anumber.getCellData(index));			
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Succesfully updated");
			
			Update();
			
			txt_type.setText("");
			txt_remark.setText("");
			txt_anumber.setText("");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
    }
    
    public void Update() {
    	col_anumber.setCellValueFactory(new PropertyValueFactory<complaints, Integer>("accno"));
		col_type.setCellValueFactory(new PropertyValueFactory<complaints, String>("type"));
		col_remark.setCellValueFactory(new PropertyValueFactory<complaints, String>("remark"));
		listM = sqlconnect.getDatacomplaints();
		table_complaints.setItems(listM);
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
		Update();
		
	}

}
