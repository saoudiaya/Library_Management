package application.listmember;

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

import aaplication.listbook.book_listController;
import aaplication.listbook.book_listController.Book;
import application.add_members.add_membersController;
import application.addbook.MainSceneController;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;

public class member_listController implements Initializable{
	
	ObservableList<Member> list = FXCollections.observableArrayList();
	@FXML
	private TableView<Member> tableView;
	@FXML
	private TableColumn<Member,String> nameCol;
	@FXML
	private TableColumn<Member,String> surnameCol;
	@FXML
	private TableColumn<Member,Integer> cinCol;
	@FXML
	private TableColumn<Member,Integer> mobileCol;
	@FXML
	private TableColumn<Member,Integer> creditCol;
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
		 String sql = "SELECT * FROM lecteur";
		 
			ResultSet rs = statement.executeQuery(sql);
			try {
				while(rs.next()) {
					 String name = rs.getString("nom");
					 String surname = rs.getString("prenom");
					 Integer cin = rs.getInt("cin");
					 Integer mobile = rs.getInt("numtel");
					 Integer credit = rs.getInt("credit");
					 
					 list.add(new Member(name,surname,cin,mobile,credit));
		        }
				
			}catch (SQLException ex) {
	            Logger.getLogger(book_listController.class.getName()).log(Level.SEVERE, null, ex);
	        }
			
			tableView.setItems(list);
		
	}


	private void initCol() {
		
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
		cinCol.setCellValueFactory(new PropertyValueFactory<>("cin"));
		mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));

	}

	public static class Member{
		
	private final SimpleStringProperty  name;
	private final SimpleStringProperty surname;
	private final SimpleIntegerProperty cin;
	private final SimpleIntegerProperty  mobile;
	private final SimpleIntegerProperty credit;
	
	public Member(String name, String surname, Integer cin,Integer mobile, Integer credit){
		this.name = new SimpleStringProperty(name);
		this.surname = new SimpleStringProperty(surname);
		this.cin = new SimpleIntegerProperty(cin);
		this.mobile = new SimpleIntegerProperty(mobile);
		this.credit = new SimpleIntegerProperty(credit);
		
	}

	public String getName() {
		return name.get();
	}

	public String getSurname() {
		return surname.get();
	}

	public Integer getCin() {
		return cin.get();
	}

	public Integer getMobile() {
		return mobile.get();
	}

	public Integer getCredit() {
		return credit.get();
	}


	}
	
	 @FXML
	    void handleBookMemberOption(ActionEvent event) {

		 Member selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
		 if(selectedForDeletion == null) {
			 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	    		alert1.setTitle("No member selected");
				alert1.setHeaderText(null);
				alert1.setContentText("Please select a member for deletion");
				alert1.showAndWait();
			 return;
		 }
		 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    	alert.setTitle("Deleting Member");
	    	alert.setHeaderText(null);
	    	alert.setContentText("Are you sure want to delete the member "+ selectedForDeletion.getName()+ " "+ selectedForDeletion.getSurname()+"?");
	    
	    	Optional<ButtonType> response = alert.showAndWait();
	    	if(response.get() == ButtonType.OK) {
	    		 Boolean resl = deleteMember(selectedForDeletion,selectedForDeletion.getCin());
	    		 if(resl) {
	    			 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	 	    		alert1.setTitle("Success");
	 				alert1.setHeaderText(null);
	 				alert1.setContentText("Member Deleted");
	 				alert1.showAndWait();
	 				list.remove(selectedForDeletion);
	    		 }else {
	    			 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
		 	    		alert1.setTitle("Failed");
		 				alert1.setHeaderText(null);
		 				alert1.setContentText("Member Not Deleted");
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

	 public boolean deleteMember(Member member,Integer id) {
		 try {
			
			 DataBaseHandler connectionClass = new DataBaseHandler();
    		 Connection connection = connectionClass.connect();
    		 Statement statement = connection.createStatement();
    		 String sql="DELETE FROM lecteur WHERE cin = '"+id+"'";
    		 String rq = "DELETE FROM abonnement WHERE cin = '"+id+"'";
    		 String rq2 = "DELETE FROM emprunter WHERE cin = '"+id+"'";
    		 int rs = statement.executeUpdate(sql);
    		 int rs1 = statement.executeUpdate(rq);
    		 int rs2 = statement.executeUpdate(rq2);
    		System.out.println(rs);
    		return true;
		 }catch (SQLException ex) {
	            Logger.getLogger(book_listController.class.getName()).log(Level.SEVERE, null, ex);
	        }
		 return false;
	 }

	 @FXML
	    void handleEditMemberOption(ActionEvent event) {
		 Member selectedForEdit = tableView.getSelectionModel().getSelectedItem();
		 
		 if(selectedForEdit == null) {
			 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
	    		alert1.setTitle("No Member selected");
				alert1.setHeaderText(null);
				alert1.setContentText("Please select a member for edit");
				alert1.showAndWait();
			 return;
		 }
		 try {
			 	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/add_members/add_members.fxml"));
				Parent parent = loader.load();
				
				add_membersController controller = (add_membersController)loader.getController();
				controller.inflateUI(selectedForEdit);
				
				Stage stage = new Stage(StageStyle.DECORATED);
				stage.setTitle("Edit Member");
				stage.setScene(new Scene(parent));
				stage.show();
				LibraryAssistantUtil.setStageIcon(stage);
				stage.setOnCloseRequest((e)->{
					try {
						handleRefreshMemberOption(new ActionEvent());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});
			} catch (IOException e) {
				Logger.getLogger(add_membersController.class.getName()).log(Level.SEVERE, null, e);
				
			}
		 
		 
	    }
	 public static boolean updateMember(Member member) throws SQLException {
    	 DataBaseHandler connectionClass = new DataBaseHandler();
		 Connection connection = connectionClass.connect();
		 Statement statement = connection.createStatement();
		 String update = "UPDATE lecteur SET nom ='"+member.getName()+"' , prenom ='"+member.getSurname()+"', cin ='"+member.getCin()+"' , numtel ='"+member.getMobile()+"',credit='"+member.getCredit()+"' WHERE cin='"+member.getCin()+"'";
		 int rs = statement.executeUpdate(update);
		 return (rs>0);
    }
	   @FXML
	    void handleRefreshMemberOption(ActionEvent event) throws SQLException {
		   loadData();
	    }

}
