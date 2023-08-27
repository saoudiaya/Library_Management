 package application.desktop.toolbar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.desktop.Main_desktopController;
import application.util.LibraryAssistantUtil;
import javafx.event.ActionEvent;

public class ToolbarController {

	@FXML
    void loadAddBook(ActionEvent event) {
		LibraryAssistantUtil.loadWindow(getClass().getResource("/application/addbook/MainScene.fxml"), "Add New Book",null);
    }

    @FXML
    void loadAddMember(ActionEvent event) {
    	LibraryAssistantUtil.loadWindow(getClass().getResource("/application/add_members/add_members.fxml"), "Add New Member",null);
    }

    @FXML
    void loadBookTable(ActionEvent event) {
    	LibraryAssistantUtil.loadWindow(getClass().getResource("/aaplication/listbook/book_list.fxml"), "Book List",null);
    }

    @FXML
    void loadMemberTable(ActionEvent event) {
    	LibraryAssistantUtil.loadWindow(getClass().getResource("/application/listmember/member_list.fxml"), "Member List",null);
    }
    
    
}
