package com.toolbox;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.toolbox.model.SourceData;
import com.toolbox.view.ApplicationHomeController;
import com.toolbox.view.EncryptionHomeController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    
	//Attributes & objects used throughout the application
    private ObservableList<SourceData> srcFileList = FXCollections.observableArrayList();
    private KeyGenerator kgen;
	private SecretKey skey;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Video Encryption Software");

        initRootLayout();
        showApplicationHome();
	}

	
	//Method to initialize the Root Layout
    public void initRootLayout() {
        try {
            // Load root layout from FMXL file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Give the controller access to the main app
            ApplicationHomeController controller = loader.getController();
            controller.setMainApp(this);

            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    //Method to display encryption home page within root layout 
    public void showEncryptionHome() {
        try {
            // Load encryption home & footer
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EncryptionHome.fxml"));
            AnchorPane encryptionHome = (AnchorPane) loader.load();

            // Set encryption home & footer into root layout
            rootLayout.setCenter(encryptionHome);
            
            // Give the controller access to the main app
            EncryptionHomeController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
  //Method to display home page
    public void showApplicationHome() {
        try {
            FXMLLoader homeLoader = new FXMLLoader();
            homeLoader.setLocation(MainApp.class.getResource("view/ApplicationHome.fxml"));
            AnchorPane applicationHome = (AnchorPane) homeLoader.load();

            // Set encryption home & footer into root layout
            rootLayout.setCenter(applicationHome);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    //Method to get the main/primary stage
    public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	
	//Getter method for SourceData Observable List
    public ObservableList<SourceData> getSrcFileList() {
		return srcFileList;
	}

    
    //Method to add a new record to SourceData Observable List
	public void addToSrcFileList(SourceData sourceData) {
		this.srcFileList.add(sourceData);
	}
	
	
	//Method to generate encryption key for the current application instance
	public void generateEncKey() {
		try {
			this.kgen = KeyGenerator.getInstance("AES");
			this.skey = kgen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	
	//Method to get the encryption key
	public SecretKey getSkey() {
		return this.skey;
	}
    

	//Main method
	public static void main(String[] args) {
		launch(args);
	}
}
