package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data; 


  
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList;  
import javafx.geometry.Side;  
import javafx.scene.Scene;  
import javafx.scene.chart.PieChart;  
import javafx.scene.chart.PieChart.Data;  
import javafx.scene.layout.StackPane;  
import javafx.stage.Stage;  


public class DataBaseHandler {
	
	private static Connection c;
	
	String url = "jdbc:mysql://localhost/libraryjava";
	String user = "root";
	String password = "";
	
	public DataBaseHandler() {
		
	}
	
	public Connection connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c =(Connection) DriverManager.getConnection(url, user, password);
			System.out.println("Connection succeed ---------");
			
		} catch(SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Connection failed ---------");
			
		}
		return c;
	}
	
	public static ObservableList<PieChart.Data> getBookGraphStatistics() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
        	DataBaseHandler connectionClass = new DataBaseHandler();
    		Connection connection = connectionClass.connect();
        	Statement statement = connection.createStatement();
            String qu1 = "SELECT COUNT(*) FROM livre";
            String qu2 = "SELECT COUNT(*) FROM emprunter";
            ResultSet rs = statement.executeQuery(qu1);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.addAll(new PieChart.Data("Book("+count+")", count));
            }
            rs = statement.executeQuery(qu2);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.addAll(new PieChart.Data("Issued("+count+")", count));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ObservableList<PieChart.Data> getMemberGraphStatistics() {
    	ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
        	DataBaseHandler connectionClass = new DataBaseHandler();
    		Connection connection = connectionClass.connect();
        	Statement statement = connection.createStatement();
            String qu1 = "SELECT COUNT(*) FROM lecteur";
            String qu2 = "SELECT COUNT(DISTINCT cin) FROM emprunter";
            ResultSet rs = statement.executeQuery(qu1);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.addAll(new PieChart.Data("Membr("+count+")", count));
            }
            rs = statement.executeQuery(qu2);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.addAll(new PieChart.Data("Activ("+count+")", count));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return data;
        
    }
	

}
