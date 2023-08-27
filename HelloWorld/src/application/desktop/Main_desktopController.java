package application.desktop;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import aaplication.listbook.book_listController;
import aaplication.listbook.book_listController.Book;
import application.add_members.add_membersController;
import application.desktop.toolbar.ToolbarController;
import application.listmember.member_listController.Member;
import application.util.LibraryAssistantUtil;
import database.DataBaseHandler;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import application.util.LibraryAssistantUtil;




public class Main_desktopController implements Initializable{
	ObservableList<BookSearch> listBook = FXCollections.observableArrayList();
	ObservableList<MemberSearch> listMember = FXCollections.observableArrayList();
	 @FXML
	    private Text MemberName;

	    @FXML
	    private Text authorName;

	    @FXML
	    private TextField bookIDInput;

	    @FXML
	    private Text bookName;

	    @FXML
	    private HBox book_info;

	    @FXML
	    private Text contactMember;

	    @FXML
	    private TextField loadMemberinfo;

	    @FXML
	    private HBox member_info;

	    @FXML
	    private TextField memberIDInput;

	    @FXML
	    private TextField bookID;
	    
	    @FXML
	    private ListView<String> issueDataList;

	    @FXML
	    private Text qtestock;
	    
	    @FXML
	    private StackPane rootPane;
	    
	    @FXML
	    private TableColumn<BookSearch,String> bookAuthorCol;

	    @FXML
	    private TableColumn<BookSearch,String> bookCategCol;
	    
	    @FXML
	    private TableColumn<BookSearch,String> bookNameCol;

	    @FXML
	    private TableColumn<BookSearch,Integer> bookQteStockCol;
	    
	    @FXML
	    private TextField searchBook;
	    
	    @FXML
	    private TextField searchMember;

	    @FXML
	    private TableView<BookSearch> tableView;
	    
	    @FXML
	    private TableColumn<MemberSearch, String> MemberNameCol;

	    @FXML
	    private TableColumn<MemberSearch, String> MembersurnamenCol;
	    
	    @FXML
	    private TableColumn<MemberSearch, Integer> membercredit;

	    @FXML
	    private TableColumn<MemberSearch, Integer> memberphoneCol;
	    
	    @FXML
	    private TableView<MemberSearch> tableViewMember;
	    
	    @FXML
	    private JFXDrawer drawer;

	    @FXML
	    private JFXHamburger hamburger;
	    @FXML
	    private Text BookAuthorHolder;

	    @FXML
	    private Text BookIDHolder;

	    @FXML
	    private Text BookNameHolder;

	    @FXML
	    private Text BookQuantityInStockHolder;

	    @FXML
	    private Text DateIssueHolder;

	    @FXML
	    private Text MemberCreditHolder;

	    @FXML
	    private Text MemberIDHolder;

	    @FXML
	    private Text MemberMobileHolder;

	    @FXML
	    private Text MemberNameHolder;

	    @FXML
	    private Text NoofDaysHolder;
	    @FXML
	    private Text NumberofRenewHolder;
	    @FXML
	    private Button renewButton;
	    @FXML
	    private Button submissionButton;
	    @FXML
	    private HBox submissionDataContainer;
	    @FXML
	    private StackPane MemberInfoContainer;
	    @FXML
	    private StackPane BookInfoContainer;

	    
	    Boolean isReadyForSubmission = false;
	    Integer renewcount = 0;
    
    DataBaseHandler connectionClass = new DataBaseHandler();
	Connection connection = connectionClass.connect();
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		DataBaseHandler connectionClass = new DataBaseHandler();
		initCol();
		try {
			initDrawer();
			initGraphe();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

    private void initGraphe() throws SQLException {
    	
    	 PieChart piechart = new PieChart();  
    	 PieChart piechart1 = new PieChart(); 
    	 
    	 
    	    piechart.setData(DataBaseHandler.getBookGraphStatistics());  
    	    BookInfoContainer.getChildren().add(piechart);
    	    
    	    piechart1.setData(DataBaseHandler.getMemberGraphStatistics());  
    	    MemberInfoContainer.getChildren().add(piechart1);

	}

	private void initDrawer() throws IOException {
    	 try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/desktop/toolbar/Toolbar.fxml"));
             VBox toolbar = loader.load();
             drawer.setSidePane(toolbar);
            
         } catch (IOException ex) {
             Logger.getLogger(Main_desktopController.class.getName()).log(Level.SEVERE, null, ex);
         }
         HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
         task.setRate(-1);
         hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
             task.setRate(task.getRate() * -1);
             task.play();
             if(drawer.isClosed()) {
            	 drawer.open();
             }else {
            	 drawer.close();
             }
         });
        
	}

	
    
    @FXML
    void loadBookinfo(ActionEvent event) throws SQLException {
    	
    	clearBookCache();
    	String id = bookIDInput.getText();
    	String qu = "SELECT * FROM livre WHERE code='"+id+"'";
    	
    	Statement statement = connection.createStatement();
    	ResultSet rs = statement.executeQuery(qu);
    	Boolean flag = false;
    	try {
			while(rs.next()) {
				 String bName = rs.getString("titre");
				 String bAuthor = rs.getString("auteur");
				 String bQteStock = rs.getString("qtestock");
				 
				 bookName.setText(bName);
				 authorName.setText(bAuthor);
				 qtestock.setText(bQteStock);
				 flag = true;
				 }
			if(!flag) {
				bookName.setText("No such Book Available");
			}
			
		}catch (SQLException ex) {
            Logger.getLogger(Main_desktopController.class.getName()).log(Level.SEVERE, null, ex);
        }
    	
    }
    
    void clearBookCache() {
    	bookName.setText("");
    	authorName.setText("");
    	qtestock.setText("");
    }
    
    void clearMemberCache() {
    	 MemberName.setText("");
		 contactMember.setText("");
    }
   
    @FXML
    void loadMemberinfo(ActionEvent event) throws SQLException {
    	clearMemberCache();
    	String id = memberIDInput.getText();
    	String qu = "SELECT * FROM lecteur WHERE cin='"+id+"'";
    	
    	Statement statement = connection.createStatement();
    	ResultSet rs = statement.executeQuery(qu);
    	Boolean flag = false;
    	try {
			while(rs.next()) {
				 String MName = rs.getString("nom");
				 String MSurname = rs.getString("prenom");
				 String Mmobile = rs.getString("numtel");
				 
				 
				 MemberName.setText(MName+ " " +MSurname);
				 contactMember.setText(Mmobile);
				 
				 flag = true;
				 }
			if(!flag) {
				MemberName.setText("No such Member Available");
			}
			
		}catch (SQLException ex) {
            Logger.getLogger(Main_desktopController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void loadIssueOperation(ActionEvent event) throws SQLException {
    	String memberID = memberIDInput.getText();
    	String bookId = bookIDInput.getText();
    	
    	if(memberID.isEmpty() || bookId.isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("Please Enter All Fields");
			alert.showAndWait();
			return;
    	}
    	
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Confirm Issue Operation");
    	alert.setHeaderText(null);
    	alert.setContentText("Are you sure want to issue the book " + bookName.getText()+ "\n to "+ MemberName.getText() + "?");
    
    	Optional<ButtonType> response = alert.showAndWait();
    	if(response.get() == ButtonType.OK) {
    		String sql = "INSERT INTO emprunter (cin,code,date_emprunt) VALUES('"+memberID+"','"+bookId+"', NOW())";
    		String qu = "UPDATE livre SET qtestock = qtestock -1 WHERE code='"+bookId+"'";
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.executeUpdate(qu);
		
			if(statement.execute(sql)) {
				Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
				alert1.setTitle("Success");
				alert1.setHeaderText(null);
				alert1.setContentText("Book Issue Complete");
				alert1.showAndWait();
			}else {
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("Failed");
				alert1.setHeaderText(null);
				alert1.setContentText("Issue Operation Failed");
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
    
    // View Issued Book 
    @FXML
    void loadBookinfo2(ActionEvent event) throws SQLException {
    	
    	clearEntree();
    	Statement statement = connection.createStatement();
    	ObservableList<String> issueData = FXCollections.observableArrayList();
    	isReadyForSubmission = false;
    	try {
    		String bookid = bookID.getText();  
        	
        	String myQuery="SELECT emprunter.code, emprunter.cin,emprunter.date_emprunt,lecteur.nom,lecteur.prenom,lecteur.numtel,lecteur.credit,livre.titre,livre.auteur,livre.qtestock from emprunter left join lecteur on emprunter.cin=lecteur.cin left join livre on emprunter.code=livre.code where emprunter.code='"+bookid+"'";
        	ResultSet rs = statement.executeQuery(myQuery);
        	if(rs.next()) {
        		MemberIDHolder.setText(rs.getString("cin"));
        		MemberNameHolder.setText(rs.getString("nom")+ " " + rs.getString("prenom"));
        		MemberMobileHolder.setText(rs.getString("numtel"));
        		MemberCreditHolder.setText(rs.getString("credit"));
        		
        		BookIDHolder.setText(rs.getString("code"));
        		BookNameHolder.setText(rs.getString("titre"));
        		BookAuthorHolder.setText(rs.getString("auteur"));
        		BookQuantityInStockHolder.setText(rs.getString("qtestock"));
        		
        		DateIssueHolder.setText(rs.getString("date_emprunt"));
        		
        		NumberofRenewHolder.setText("N° Renew Book: " + renewcount);
        		java.sql.Timestamp mIssueTime = rs.getTimestamp("date_emprunt");
        		
        		Long timeElapsed = System.currentTimeMillis() - mIssueTime.getTime();
                Long days = TimeUnit.DAYS.convert(timeElapsed, TimeUnit.MILLISECONDS) + 1;
                String daysElapsed = String.format("Used %d days", days);
                NoofDaysHolder.setText(daysElapsed);
                if(days>=7) {
                	Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        			alert1.setTitle("Attention");
        			alert1.setHeaderText(null);
        			alert1.setContentText("This Book Should Be Returned");
        			alert1.showAndWait();
                }
                
                isReadyForSubmission = true;
                disableEnableControls(true);
                
        	}else {
        	   	LibraryAssistantUtil.loadWindow(getClass().getResource("/application/alert/Alert.fxml"), "Attention",null);
        	}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    
    private void clearEntree() {
    	MemberIDHolder.setText("");
		MemberNameHolder.setText("");
		MemberMobileHolder.setText("");
		MemberCreditHolder.setText("");
		
		BookIDHolder.setText("");
		BookNameHolder.setText("");
		BookAuthorHolder.setText("");
		BookQuantityInStockHolder.setText("");
		
		DateIssueHolder.setText("");
		NoofDaysHolder.setText("");
		NumberofRenewHolder.setText("");
		
		disableEnableControls(false);
	}
    private void disableEnableControls(Boolean enableFlag) {
    	if(enableFlag) {
    		renewButton.setDisable(false);
    		submissionButton.setDisable(false);
    	}else {
    		renewButton.setDisable(true);
    		submissionButton.setDisable(true);
    	}
    }

	@FXML
    void loadSubmissionOp(ActionEvent event) throws SQLException {
    	if(!isReadyForSubmission) {
    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
    		alert1.setTitle("Failed");
			alert1.setHeaderText(null);
			alert1.setContentText("Please select a book to submit");
			alert1.showAndWait();
    	}
    	
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Confirm Submit Operation");
    	alert.setHeaderText(null);
    	alert.setContentText("Are you sure want to submit the book ?");
    
    Optional<ButtonType> response = alert.showAndWait();
    	if(response.get() == ButtonType.OK) {
    	String bookid = bookID.getText();
    	String ac1 = "DELETE FROM emprunter WHERE code='"+bookid+"'";
    	String ac2 = "UPDATE livre SET qtestock = qtestock +1 WHERE code='"+bookid+"'";
    	
    	Statement statement = connection.createStatement();
		statement.executeUpdate(ac1);
		statement.executeUpdate(ac2);
		
		String sql="SELECT * FROM emprunter;";
		ResultSet resultSet = statement.executeQuery(sql);
		
		
		if(!statement.execute(ac1)) {
			Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
			alert1.setTitle("Success");
			alert1.setHeaderText(null);
			alert1.setContentText("Book Has Been Submitted");
			alert1.showAndWait();
			loadBookinfo2(null);
		}else {
			Alert alert1 = new Alert(Alert.AlertType.ERROR);
			alert1.setTitle("Failed");
			alert1.setHeaderText(null);
			alert1.setContentText("Submission Has Been Failed");
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
    
    @FXML
    void loadRenewOp(ActionEvent event) throws SQLException {
    	if(!isReadyForSubmission) {
    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
    		alert1.setTitle("Failed");
			alert1.setHeaderText(null);
			alert1.setContentText("Please select a book to renew");
			alert1.showAndWait();
    	}
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Confirm Renew Operation");
    	alert.setHeaderText(null);
    	alert.setContentText("Are you sure want to renew the book ?");
    
    Optional<ButtonType> response = alert.showAndWait();
    	if(response.get() == ButtonType.OK) {
    		String ac = "UPDATE emprunter SET date_emprunt = NOW() WHERE code = '"+ bookID.getText()+"'";
    		renewcount+=1;
    		Statement statement = connection.createStatement();
    		statement.executeUpdate(ac);
    		
    		String sql="SELECT * FROM emprunter;";
    		ResultSet resultSet = statement.executeQuery(sql);
    		
    		
    		if(!statement.execute(ac)) {
    			
    			Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
    			alert1.setTitle("Success");
    			alert1.setHeaderText(null);
    			alert1.setContentText("Book Has Been Renewed");
    			alert1.showAndWait();
    			loadBookinfo2(null);
    		}else {
    			Alert alert1 = new Alert(Alert.AlertType.ERROR);
    			alert1.setTitle("Failed");
    			alert1.setHeaderText(null);
    			alert1.setContentText("Renew Has Been Failed");
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
    
    
    @FXML
    void handleMenuClose(ActionEvent event) {
    	((Stage)rootPane.getScene().getWindow()).close();
    }
    @FXML
    void handleMenuAddBook(ActionEvent event) {
    	LibraryAssistantUtil.loadWindow(getClass().getResource("/application/addbook/MainScene.fxml"), "Add New Book",null);
    }

    @FXML
    void handleMenuAddMember(ActionEvent event) {
    	LibraryAssistantUtil.loadWindow(getClass().getResource("/application/add_members/add_members.fxml"), "Add New Member",null);
    }

    @FXML
    void handleMenuFullScreen(ActionEvent event) {
    	Stage stage = ((Stage)rootPane.getScene().getWindow());
    	stage.setFullScreen(!stage.isFullScreen());
    }

    @FXML
    void handleMenuViewBook(ActionEvent event) {
    	LibraryAssistantUtil.loadWindow(getClass().getResource("/aaplication/listbook/book_list.fxml"), "Book List",null);
    }

    @FXML
    void handleMenuViewMember(ActionEvent event) {
    	LibraryAssistantUtil.loadWindow(getClass().getResource("/application/listmember/member_list.fxml"), "Member List",null);
    }
    

    @FXML
    void lookBookSearch(ActionEvent event) throws SQLException {
    	
    	loadData();
    	
    }

    @FXML
    void lookMemberSearch(ActionEvent event) throws SQLException {
    	loadData2();
    }
    
    
    //Search Book
    
    private void loadData() throws SQLException{
    	String bookInput = searchBook.getText(); 
    	System.out.println(bookInput);
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
						 listBook.add(new BookSearch(title,author,qtestock,""));
						
					 }else {
						 listBook.add(new BookSearch(title,author,qtestock,category));
					 }
						
					 
		        }
				
			}catch (SQLException ex) {
	            Logger.getLogger(Main_desktopController.class.getName()).log(Level.SEVERE, null, ex);
	        }
			if(bookInput.isEmpty()) {
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("Failed");
				alert1.setHeaderText(null);
				alert1.setContentText("Please Enter Field");
				alert1.showAndWait();
			}else {
			List<BookSearch> someList = listBook.stream().filter(p-> p.getTitre().startsWith(bookInput)||p.getTitre().equals(bookInput) || p.getAuteur().equals(bookInput)).collect(Collectors.toList());
	    	tableView.getItems().setAll(someList);
			}
	}
    
    
    
    private void initCol() {
		
    	bookNameCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
    	bookAuthorCol.setCellValueFactory(new PropertyValueFactory<>("auteur"));
    	bookQteStockCol.setCellValueFactory(new PropertyValueFactory<>("qtestock"));
    	bookCategCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    	

    	MemberNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    	MembersurnamenCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
    	memberphoneCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    	membercredit.setCellValueFactory(new PropertyValueFactory<>("credit"));


	}
    public static class BookSearch{
		
    	private final SimpleStringProperty  titre;
    	private final SimpleStringProperty auteur;
    	private final SimpleIntegerProperty qtestock;
    	private final SimpleStringProperty description;
    	
    	public BookSearch(String titre,String auteur,Integer qtestock,String description){
    		this.titre = new SimpleStringProperty(titre);
    		this.auteur = new SimpleStringProperty(auteur);
    		this.qtestock = new SimpleIntegerProperty(qtestock);
    		this.description = new SimpleStringProperty(description);
    		
    	}

    	public String getTitre() {
    		return titre.get();
    	}
    	public String getAuteur() {
    		return auteur.get();
    	}

    	public Integer getQtestock() {
    		return qtestock.get();
    	}

    	public String getDescription() {
    		return description.get();
    	}

		@Override
		public String toString() {
			return "BookSearch [titre=" + titre + ", auteur=" + auteur + ", qtestock=" + qtestock + ", description="
					+ description + "]";
		}

    	
    	}
    
    //Search member
    private void loadData2() throws SQLException{
    	String memberInput = searchMember.getText();
		 DataBaseHandler connectionClass = new DataBaseHandler();
		 Connection connection = connectionClass.connect();
		 Statement statement = connection.createStatement();	
		 String sql = "SELECT * FROM lecteur";
		 
			ResultSet rs = statement.executeQuery(sql);
			try {
				while(rs.next()) {
					 String name = rs.getString("nom");
					 String surname = rs.getString("prenom");
					 Integer mobile = rs.getInt("numtel");
					 Integer credit = rs.getInt("credit");
					 
					 listMember.add(new MemberSearch(name,surname,mobile,credit));
		        }
				
			}catch (SQLException ex) {
	            Logger.getLogger(book_listController.class.getName()).log(Level.SEVERE, null, ex);
	        }
			if(memberInput.isEmpty()) {
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("Failed");
				alert1.setHeaderText(null);
				alert1.setContentText("Please Enter Field");
				alert1.showAndWait();
			}else {
				
				List<MemberSearch> someList = listMember.stream().filter(p-> p.getName().startsWith(memberInput)||p.getName().equals(memberInput) || p.getSurname().equals(memberInput)).collect(Collectors.toList());
				tableViewMember.getItems().setAll(someList);
			}
			
		
	}


	

	public static class MemberSearch{
		
	private final SimpleStringProperty  name;
	private final SimpleStringProperty surname;
	private final SimpleIntegerProperty  mobile;
	private final SimpleIntegerProperty credit;
	
	public MemberSearch(String name, String surname,Integer mobile, Integer credit){
		this.name = new SimpleStringProperty(name);
		this.surname = new SimpleStringProperty(surname);
		this.mobile = new SimpleIntegerProperty(mobile);
		this.credit = new SimpleIntegerProperty(credit);
		
	}

	public String getName() {
		return name.get();
	}

	public String getSurname() {
		return surname.get();
	}

	public Integer getMobile() {
		return mobile.get();
	}

	public Integer getCredit() {
		return credit.get();
	}

	@Override
	public String toString() {
		return "MemberSearch [name=" + name + ", surname=" + surname + ", mobile=" + mobile + ", credit=" + credit
				+ "]";
	}
	

	}
	
}
