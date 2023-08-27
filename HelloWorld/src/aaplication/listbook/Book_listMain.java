package aaplication.listbook;


import application.util.LibraryAssistantUtil;
import database.DataBaseHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Book_listMain extends Application {
@Override
public void start(Stage primaryStage) {
	try {
		Parent root = FXMLLoader.load(getClass().getResource("book_list.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Library");
		primaryStage.setScene(scene);
		primaryStage.show();
		LibraryAssistantUtil.setStageIcon(primaryStage);
		DataBaseHandler db = new DataBaseHandler();
		db.connect();
	} catch(Exception e) {
		e.printStackTrace();
	}
}

public static void main(String[] args) {
	launch(args);
}
}
