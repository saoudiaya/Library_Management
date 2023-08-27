package application.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.desktop.Main_desktopController;
import application.util.LibraryAssistantUtil;
import javafx.event.ActionEvent;

public class LoginController implements Initializable{
	@FXML
    private AnchorPane root;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	

	

	// Event Listener on Button.onAction
	@FXML
	public void handleLoginButtonAction(ActionEvent event) {
		
		
		String user = username.getText();
		String pass = password.getText();
		 
		if(user.equals("admin") && pass.equals("javaensit")) {
			closeStage();
			loadMain();
		}else {
			username.getStyleClass().add("wrong-credentials");
			password.getStyleClass().add("wrong-credentials");

		}
	}
	private void closeStage() {
		((Stage)username.getScene().getWindow()).close();
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleCancelButtonAction(ActionEvent event) {
		System.exit(0);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
    void loadMain() {
    	try {
			Parent parent = FXMLLoader.load(getClass().getResource("/application/desktop/Main_desktop.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Library Assistant");
			stage.setScene(new Scene(parent));
			stage.show();
			LibraryAssistantUtil.setStageIcon(stage);
		} catch (IOException e) {
			Logger.getLogger(Main_desktopController.class.getName()).log(Level.SEVERE, null, e);
			
		}
    	
    }
}
