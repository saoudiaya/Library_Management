package application.add_members;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import aaplication.listbook.book_listController;
import application.addbook.MainSceneController;
import application.desktop.Main_desktopController;
import application.listmember.member_listController;
import application.util.LibraryAssistantUtil;
import database.DataBaseHandler;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class add_membersController {
	@FXML
	private AnchorPane rootPane;
	@FXML
	private TextField name;
	@FXML
	private TextField surname;
	@FXML
	private TextField cin;
	@FXML
	private TextField mobile;
	@FXML
	private TextField credit;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;
	
	private Boolean ifInEditMode = Boolean.FALSE;

	// Event Listener on Button[#saveButton].onAction
		@FXML
		public void addMember(ActionEvent event) throws SQLException {
			DataBaseHandler connectionClass = new DataBaseHandler();
			Connection connection = connectionClass.connect();
			
			if(name.getText().isEmpty() || surname.getText().isEmpty() || cin.getText().isEmpty() || mobile.getText().isEmpty() || credit.getText().isEmpty()) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Please Enter All Fields");
				alert.showAndWait();
				return;
			}
			
			if(ifInEditMode) {
				handleEditOption();
				return;
			}
			
			String sql = "INSERT INTO lecteur (cin,nom,prenom,numtel,credit) VALUES('"+cin.getText()+"','"+name.getText()+"','"+surname.getText()+"','"+mobile.getText()+"','"+credit.getText()+"')";
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			
			sql="SELECT * FROM lecteur;";
			ResultSet resultSet = statement.executeQuery(sql);
			
			try {
				while(resultSet.next()) {
					String nom = resultSet.getString("nom");
					System.out.println(nom);
				}
			}catch(SQLException E) {
				Logger.getLogger(add_membersController.class.getName()).log(Level.SEVERE, null, E);
			}
			
			if(statement.execute(sql)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setContentText("Success");
				alert.showAndWait();
				loadWindow("/application/addAbonn/AddAbonn.fxml", "Add Subscription");
			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Failed");
				alert.showAndWait();
			}
			
		}
		
		private void handleEditOption() throws SQLException {
			Integer id = Integer.valueOf(cin.getText());
			Integer phone = Integer.valueOf(mobile.getText());
			Integer crd = Integer.valueOf(credit.getText());
			String nom = name.getText();
			String prenom = surname.getText();
			
		
			member_listController.Member member = new member_listController.Member(nom,prenom,id,phone,crd);
			
			if(member_listController.updateMember(member)) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setHeaderText(null);
				alert.setContentText("Member Updated");
				alert.showAndWait();
			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Failed");
				alert.setHeaderText(null);
				alert.setContentText("Can't Update The Member");
				alert.showAndWait();
			}
		}
		// Event Listener on Button[#cancelButton].onAction
		@FXML
		public void cancelMember(ActionEvent event) {
			Stage stage = (Stage) rootPane.getScene().getWindow();
	        stage.close();
		}
		

		public void inflateUI(member_listController.Member member) {
			name.setText(member.getName());
			cin.setText(String.valueOf(member.getCin()));
			surname.setText(member.getSurname());
			mobile.setText(String.valueOf(member.getMobile()));
			credit.setText(String.valueOf(member.getCredit()));
			cin.setEditable(false);
			ifInEditMode = Boolean.TRUE;
		}
		
		 void loadWindow(String loc, String title) {
		    	try {
					Parent parent = FXMLLoader.load(getClass().getResource(loc));
					Stage stage = new Stage(StageStyle.DECORATED);
					stage.setTitle(title);
					stage.setScene(new Scene(parent));
					stage.show();
					LibraryAssistantUtil.setStageIcon(stage);
				} catch (IOException e) {
					Logger.getLogger(Main_desktopController.class.getName()).log(Level.SEVERE, null, e);
					
				}
		    	  
		    }
}
