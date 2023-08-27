package application.addAbonn;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.add_members.add_membersController;
import database.DataBaseHandler;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddAbonnController {
	@FXML
	private AnchorPane rootPane;
	@FXML
	private TextField cinID;
	@FXML
	private TextField fraisAbonn;
	@FXML
	private DatePicker dateAbonn;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void addAbonnement(ActionEvent event) throws SQLException {
		LocalDate datecreation = dateAbonn.getValue();
		DataBaseHandler connectionClass = new DataBaseHandler();
		Connection connection = connectionClass.connect();
		
		if(cinID.getText().isEmpty() || fraisAbonn.getText().isEmpty() || datecreation.toString().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter All Fields");
			alert.showAndWait();
			return;
		}
		
		String sql = "INSERT INTO abonnement (frais,cin,date_creation) VALUES('"+fraisAbonn.getText()+"','"+cinID.getText()+"','"+datecreation.toString()+"')";
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
		
		sql="SELECT * FROM abonnement;";
		ResultSet resultSet = statement.executeQuery(sql);
		
		try {
			while(resultSet.next()) {
				String cin = resultSet.getString("cin");
				System.out.println(cin);
			}
		}catch(SQLException E) {
			Logger.getLogger(add_membersController.class.getName()).log(Level.SEVERE, null, E);
		}
		
		if(statement.execute(sql)) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Success");
			alert.showAndWait();
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Failed");
			alert.showAndWait();
		}
		
	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void cancelAbonnement(ActionEvent event) {
		Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
	}
}
