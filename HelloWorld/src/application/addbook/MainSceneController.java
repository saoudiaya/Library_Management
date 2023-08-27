package application.addbook;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import aaplication.listbook.book_listController;
import database.DataBaseHandler;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainSceneController {
	@FXML
	private AnchorPane rootPane;
	@FXML
	private TextField titre;
	@FXML
	private TextField code;
	@FXML
	private TextField auteur;
	@FXML
	private TextField qteStock;
	@FXML
	private TextField isbn;
	@FXML
	private TextField category;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;
	
	private Boolean ifInEditMode = Boolean.FALSE;

	// Event Listener on Button[#saveButton].onAction
	@FXML
	public void addBook(ActionEvent event) throws SQLException {
		DataBaseHandler connectionClass = new DataBaseHandler();
		Connection connection = connectionClass.connect();
		
		if(titre.getText().isEmpty() || code.getText().isEmpty() || auteur.getText().isEmpty() || isbn.getText().isEmpty() || qteStock.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter All Fields");
			alert.showAndWait();
			return;
		}
		

		if(this.category.getText().equals("romantique")) {
			String description = "Livre Romantique";
			String sql = "INSERT INTO romantique (code,titre,auteur,isbn,qteStock,description) VALUES('"+code.getText()+"','"+titre.getText()+"','"+auteur.getText()+"','"+isbn.getText()+"','"+qteStock.getText()+"','"+description+"')";
			String rq = "INSERT INTO livre (code,titre,auteur,isbn,qteStock,description) VALUES('"+code.getText()+"','"+titre.getText()+"','"+auteur.getText()+"','"+isbn.getText()+"','"+qteStock.getText()+"','"+description+"')";

			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.executeUpdate(rq);

			sql="SELECT * FROM romantique;";
			ResultSet resultSet = statement.executeQuery(sql);
			try {
				while(resultSet.next()) {
					String titre = resultSet.getString("titre");
					System.out.println(titre);
				}
			}catch(SQLException E) {
				Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, E);
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
		if(this.category.getText().equals("policier")) {
				String description = "Livre Policier";
				String sql = "INSERT INTO policier (code,titre,auteur,isbn,qteStock,description) VALUES('"+code.getText()+"','"+titre.getText()+"','"+auteur.getText()+"','"+isbn.getText()+"','"+qteStock.getText()+"','"+description+"')";
				String rq = "INSERT INTO livre (code,titre,auteur,isbn,qteStock,description) VALUES('"+code.getText()+"','"+titre.getText()+"','"+auteur.getText()+"','"+isbn.getText()+"','"+qteStock.getText()+"','"+description+"')";

				Statement statement = connection.createStatement();
				statement.executeUpdate(sql);
				statement.executeUpdate(rq);

				
				sql="SELECT * FROM policier;";
				ResultSet resultSet = statement.executeQuery(sql);
				try {
					while(resultSet.next()) {
						String titre = resultSet.getString("titre");
						System.out.println(titre);
					}
				}catch(SQLException E) {
					Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, E);
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
		
		if(this.category.getText().equals("science")) {
			String description = "Livre Scientifique";
			String sql = "INSERT INTO scfiction (code,titre,auteur,isbn,qteStock,description) VALUES('"+code.getText()+"','"+titre.getText()+"','"+auteur.getText()+"','"+isbn.getText()+"','"+qteStock.getText()+"','"+description+"')";
			String rq = "INSERT INTO livre (code,titre,auteur,isbn,qteStock,description) VALUES('"+code.getText()+"','"+titre.getText()+"','"+auteur.getText()+"','"+isbn.getText()+"','"+qteStock.getText()+"','"+description+"')";

			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.executeUpdate(rq);

			sql="SELECT * FROM scfiction;";
			ResultSet resultSet = statement.executeQuery(sql);
			try {
				while(resultSet.next()) {
					String titre = resultSet.getString("titre");
					System.out.println(titre);
				}
			}catch(SQLException E) {
				Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, E);
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
	
		if(this.category.getText().isEmpty()) {
			
			if(ifInEditMode) {
				handleEditOption();
				return;
			}
			
			String sql = "INSERT INTO livre (code,titre,auteur,isbn,qteStock) VALUES('"+code.getText()+"','"+titre.getText()+"','"+auteur.getText()+"','"+isbn.getText()+"','"+qteStock.getText()+"')";
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			
			sql="SELECT * FROM livre;";
			ResultSet resultSet = statement.executeQuery(sql);
			
			try {
				while(resultSet.next()) {
					String titre = resultSet.getString("titre");
					System.out.println(titre);
				}
			}catch(SQLException E) {
				Logger.getLogger(MainSceneController.class.getName()).log(Level.SEVERE, null, E);
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
		
	
	}
	private void handleEditOption() throws SQLException {
		Integer id = Integer.valueOf(code.getText());
		Integer qte = Integer.valueOf(qteStock.getText());
		Integer isn = Integer.valueOf(isbn.getText());
		String title = titre.getText();
		String author = auteur.getText();
		String ch = "";
	
		book_listController.Book book = new book_listController.Book(title,id,author,qte,isn,ch);
		if(book_listController.updateBook(book)) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("Book Updated");
			alert.showAndWait();
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Failed");
			alert.setHeaderText(null);
			alert.setContentText("Can't Update The Book");
			alert.showAndWait();
		}
	}
	// Event Listener on Button[#cancelButton].onAction
	@FXML
	public void cancelBook(ActionEvent event) {
		Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
	}
	
	public void inflateUI(book_listController.Book book) {
		titre.setText(book.getTitre());
		code.setText(String.valueOf(book.getCode()));
		auteur.setText(book.getAuteur());
		qteStock.setText(String.valueOf(book.getQtestock()));
		isbn.setText(String.valueOf(book.getIsbn()));
		category.setText("");
		code.setEditable(false);
		ifInEditMode = Boolean.TRUE;
	}
}
