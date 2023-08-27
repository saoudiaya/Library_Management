package aaplication.listbook;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.addbook.MainSceneController;
import application.alert.AlertMaker;
import application.desktop.Main_desktopController;
import application.util.LibraryAssistantUtil;
import database.DataBaseHandler;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;

public class book_listController implements Initializable{
	ObservableList<Book> list = FXCollections.observableArrayList();
	@FXML
	private AnchorPane contentPane;
	@FXML
	private TableView<Book> tableView;
	@FXML
	private TableColumn<Book,String> titleCol;
	@FXML
	private TableColumn<Book,Integer> idCol;
	@FXML
	private TableColumn<Book,String> authorCol;
	@FXML
	private TableColumn<Book,Integer> isbnCol;
	@FXML
	private TableColumn<Book,Integer> qtestockCol;
	@FXML
	private TableColumn<Book,String> CtgCol;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		DataBaseHandler connectionClass = new DataBaseHandler();
		initCol();
		try {
			loadData();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	
	private void loadData() throws SQLException{
		
		list.clear();
		 DataBaseHandler connectionClass = new DataBaseHandler();
		 Connection connection = connectionClass.connect();
		 Statement statement = connection.createStatement();	
		 String sql = "SELECT * FROM livre";
		 
			ResultSet rs = statement.executeQuery(sql);
			try {
				while(rs.next()) {
					 String title = rs.getString("titre");
					 Integer id = rs.getInt("code");
					 String author = rs.getString("auteur");
					 Integer isbn = rs.getInt("isbn");
					 Integer qtestock = rs.getInt("qtestock");
					 String category = rs.getString("description");
					 
					 if(category == null) {
						 list.add(new Book(title,id,author,qtestock,isbn,""));
					 }else
						 list.add(new Book(title,id,author,qtestock,isbn,category));
		        }
				
			}catch (SQLException ex) {
	            Logger.getLogger(book_listController.class.getName()).log(Level.SEVERE, null, ex);
	        }
			
			tableView.setItems(list);
		
	}


	private void initCol() {
		
		titleCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("code"));
		authorCol.setCellValueFactory(new PropertyValueFactory<>("auteur"));
		isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		qtestockCol.setCellValueFactory(new PropertyValueFactory<>("qtestock"));
		CtgCol.setCellValueFactory(new PropertyValueFactory<>("description"));

	}

	public static class Book{
		
	private final SimpleStringProperty  titre;
	private final SimpleIntegerProperty code;
	private final SimpleStringProperty auteur;
	private final SimpleIntegerProperty  isbn;
	private final SimpleIntegerProperty qtestock;
	private final SimpleStringProperty description;
	
	public Book(String titre, Integer code, String auteur,Integer qtestock, Integer isbn,String description){
		this.titre = new SimpleStringProperty(titre);
		this.code = new SimpleIntegerProperty(code);
		this.auteur = new SimpleStringProperty(auteur);
		this.isbn = new SimpleIntegerProperty(isbn);
		this.qtestock = new SimpleIntegerProperty(qtestock);
		this.description = new SimpleStringProperty(description);
		
	}

	public String getTitre() {
		return titre.get();
	}

	public Integer getCode() {
		return code.get();
	}

	public String getAuteur() {
		return auteur.get();
	}

	public Integer getIsbn() {
		return isbn.get();
	}

	public Integer getQtestock() {
		return qtestock.get();
	}

	public String getDescription() {
		return description.get();
	}

	} 

	 @FXML
	    void handleBookDeleteOption(ActionEvent event) throws SQLException {
		 Book selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
		 
		 if(selectedForDeletion == null) {
			 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	    		alert1.setTitle("No book selected");
				alert1.setHeaderText(null);
				alert1.setContentText("Please select a book for deletion");
				alert1.showAndWait();
			 return;
		 }
		 if(isBookAlreadyIssued(selectedForDeletion,selectedForDeletion.getCode())==true) {
			 	Alert alert1 = new Alert(Alert.AlertType.ERROR);
	    		alert1.setTitle("Failed");
				alert1.setHeaderText(null);
				alert1.setContentText("Book Is Already Issued");
				alert1.showAndWait();
				return;
		 }
		 
		 	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    	alert.setTitle("Deleting Book");
	    	alert.setHeaderText(null);
	    	alert.setContentText("Are you sure want to delete the book "+ selectedForDeletion.getTitre()+ "?");
	    
	    	Optional<ButtonType> response = alert.showAndWait();
	    	if(response.get() == ButtonType.OK) {
	    		 Boolean resl = deleteBook(selectedForDeletion,selectedForDeletion.getCode());
	    		 if(resl) {
	    			 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	 	    		alert1.setTitle("Success");
	 				alert1.setHeaderText(null);
	 				alert1.setContentText("Book Deleted");
	 				alert1.showAndWait();
	 				list.remove(selectedForDeletion);
	    		 }else {
	    			 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
		 	    		alert1.setTitle("Failed");
		 				alert1.setHeaderText(null);
		 				alert1.setContentText("Book Not Deleted");
		 				alert1.showAndWait();
	    		 }
	    	}else {
	    		Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	    		alert1.setTitle("Cancel");
				alert1.setHeaderText(null);
				alert1.setContentText("Cancelled");
				alert1.showAndWait();
	    	}
	    }

	 public boolean deleteBook(Book book,Integer id) {
		 try {
			
			 DataBaseHandler connectionClass = new DataBaseHandler();
    		 Connection connection = connectionClass.connect();
    		 Statement statement = connection.createStatement();
    		 String sql="DELETE FROM livre WHERE code = '"+id+"'";
    		 int rs = statement.executeUpdate(sql);
    		 
    		System.out.println(rs);
    		return true;
		 }catch (SQLException ex) {
	            Logger.getLogger(book_listController.class.getName()).log(Level.SEVERE, null, ex);
	        }
		 return false;
	 }
	 public boolean isBookAlreadyIssued(Book book,Integer id) {
		 try {
				
			 DataBaseHandler connectionClass = new DataBaseHandler();
    		 Connection connection = connectionClass.connect();
    		 Statement statement = connection.createStatement();
    		 String sql="SELECT COUNT(*) FROM emprunter WHERE code = '"+id+"'";
    		 ResultSet rs = statement.executeQuery(sql);
    		 if(rs.next()) {
    			 int count = rs.getInt(1);
    			 if(count >0) {
    				 return true;
    			 }else {
    				 return false;
    			 } 
    		 }  
    		
		 }catch (SQLException ex) {
	            Logger.getLogger(book_listController.class.getName()).log(Level.SEVERE, null, ex);
	        }
		 return false;
	 }
	 
	    @FXML
	    void handleBookEditOption(ActionEvent event) {
	    	Book selectedForEdit = tableView.getSelectionModel().getSelectedItem();
			 
			 if(selectedForEdit == null) {
				 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
		    		alert1.setTitle("No book selected");
					alert1.setHeaderText(null);
					alert1.setContentText("Please select a book for edit");
					alert1.showAndWait();
				 return;
			 }
			 try {
				 	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/addbook/MainScene.fxml"));
					Parent parent = loader.load();
					
					MainSceneController controller = (MainSceneController)loader.getController();
					controller.inflateUI(selectedForEdit);
					
					Stage stage = new Stage(StageStyle.DECORATED);
					stage.setTitle("Edit Book");
					stage.setScene(new Scene(parent));
					stage.show();
					LibraryAssistantUtil.setStageIcon(stage);
					
					stage.setOnCloseRequest((e)->{
						try {
							handleBookRefreshOption(new ActionEvent());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					});
				} catch (IOException e) {
					Logger.getLogger(Main_desktopController.class.getName()).log(Level.SEVERE, null, e);
					
				}
			 
			 
	    }
	    
	    public static boolean updateBook(Book book) throws SQLException {
	    	 DataBaseHandler connectionClass = new DataBaseHandler();
    		 Connection connection = connectionClass.connect();
    		 Statement statement = connection.createStatement();
    		 String update = "UPDATE livre SET titre ='"+book.getTitre()+"' , auteur ='"+book.getAuteur()+"', isbn ='"+book.getIsbn()+"' , qteStock ='"+book.getQtestock()+"' WHERE code='"+book.getCode()+"'";
    		 int rs = statement.executeUpdate(update);
    		 return (rs>0);
	    }
	    
	    @FXML
	    void handleBookRefreshOption(ActionEvent event) throws SQLException {
	    	loadData();
	    }
}
