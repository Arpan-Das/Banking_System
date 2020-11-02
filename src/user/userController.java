package user;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class userController {
	@FXML
	private Button butlogout;
	
	public void logout(ActionEvent event) {
		try {
			
			((Node)event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root;
			root = loader.load(getClass().getResource("/application/AVfirst.fxml").openStream());
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("not able to load avfirst.fxml - adminController.login()");
		}
	}
}
