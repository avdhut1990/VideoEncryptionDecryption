package com.toolbox.view;

import com.toolbox.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;

public class RootLayoutController {
	
	// Reference to the main application.
	@SuppressWarnings("unused")
    private MainApp mainApp;
    
    @FXML
    private MenuItem encryptHomeMenuBtn, homeMenuBtn, exitMenuBtn, aboutMenuBtn;
    
	/**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
	public RootLayoutController() {
	}
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        encryptHomeMenuBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	mainApp.showEncryptionHome();
            }
        });

        homeMenuBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	mainApp.showApplicationHome();
            }
        });

        exitMenuBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.getPrimaryStage().close();
            }
        });

        aboutMenuBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("About Us");
        		alert.setHeaderText(null);
        		alert.setContentText("Developed By Avdhut Vaidya");
        		alert.showAndWait();
            }
        });
    }
}
