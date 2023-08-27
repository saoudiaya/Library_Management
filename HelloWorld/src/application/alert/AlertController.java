package application.alert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AlertController {
	 @FXML
	 private AnchorPane rootPane;

    @FXML
    void closeBox(ActionEvent event) {
    	Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
