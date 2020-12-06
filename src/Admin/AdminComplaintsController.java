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

public class AdminComplaintsController implements Initializable {
	
	@FXML
	private Label lab_name;
	
	@FXML
    private TableView<complaints> table_complaints;

    @FXML
    private TableColumn<complaints, Integer> col_anumber;

    @FXML
    private TableColumn<complaints, String> col_type;

    @FXML
    private TableColumn<complaints, String> col_remark;
    
    @FXML
    private TableColumn<complaints, String> col_status;
	
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
    
    public void setName(String name) {
    	lab_name.setText(name);
    }
    
    public void getSelected(MouseEvent event) {
    	index = table_complaints.getSelectionModel().getSelectedIndex();
    	if(index <= -1) {
    		return;
    	}
    	txt_anumber.setText(col_anumber.getCellData(index).toString());
    	txt_type.setText(col_type.getCellData(index).toString());
    	txt_remark.setText(col_remark.getCellData(index).toString());

    }
    
    public void Solved(ActionEvent event) {
    	
    	try {
    		
    		if(!col_status.getCellData(index).toString().equals("Solved")) {
    			conn = sqlconnect.dbconnect();
    			stmt = conn.createStatement();
    			stmt.execute("update feed_Comp set status = 'Solved' where accno = " + col_anumber.getCellData(index) +" and remark = '"+ col_remark.getCellData(index)+"'");
    			JOptionPane.showMessageDialog(null, "Solved");
    			
    			Update();
    			
    			txt_type.setText("");
    			txt_remark.setText("");
    			txt_anumber.setText("");
    		}else {
    			JOptionPane.showMessageDialog(null, "Already Solved");
    		}
    		conn.close();
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, e);
    	}
    }
    
    @FXML
    public void Delete_complaint(ActionEvent event) {
    	
    	
    	try {
    		
    		if(col_status.getCellData(index).toString().equals("Solved")) {
    			conn = sqlconnect.dbconnect();
			ps = conn.prepareStatement("delete from feed_Comp where accno = ? and remark = ? ");
			ps.setInt(1, col_anumber.getCellData(index));
			ps.setString(2, col_remark.getCellData(index));
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "Deleted");
			
			Update();
			
			txt_type.setText("");
			txt_remark.setText("");
			txt_anumber.setText("");
			
    		index = -1;
    		}else {
    			JOptionPane.showMessageDialog(null, "Please View before Deleting");
    		}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
    }
    
    public void Update() {
    	col_anumber.setCellValueFactory(new PropertyValueFactory<complaints, Integer>("accno"));
		col_type.setCellValueFactory(new PropertyValueFactory<complaints, String>("type"));
		col_remark.setCellValueFactory(new PropertyValueFactory<complaints, String>("remark")); // this is not visible
		col_status.setCellValueFactory(new PropertyValueFactory<complaints, String>("status"));
		
		listM = sqlconnect.getDatacomplaints();
		table_complaints.setItems(listM);
    }

	public void exxit(ActionEvent event) {
		System.exit(0);	
		}

    public void out(ActionEvent event) {	// logout
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

    public void out1(ActionEvent event) {		//****** go back to adminpanel
	try {
		
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/Admin/AdminPanel.fxml").openStream());
		
		AdminPanelController adminpanelController = (AdminPanelController)loader.getController();
		adminpanelController.SetAdminName(lab_name.getText());
		
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
		lab_name.setVisible(false);
	}

}
