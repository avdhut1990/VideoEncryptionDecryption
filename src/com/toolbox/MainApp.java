package com.toolbox;

import java.io.IOException;

import com.toolbox.model.SourceData;
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
    
	/**
     * The data as an observable list of sourceData
     */
    private ObservableList<SourceData> srcFileList = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Video Encryption Software");

        initRootLayout();

        showEncryptionHome();
	}
	
	
    public ObservableList<SourceData> getSrcFileList() {
		return srcFileList;
	}

	public void addToSrcFileList(SourceData sourceData) {
		this.srcFileList.add(sourceData);
	}

	
	/**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from FMXL file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Shows the encryption home inside the root layout.
     */
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

    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    

	public static void main(String[] args) {
		launch(args);
	}
}
