package com.toolbox.view;

import com.toolbox.MainApp;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class ApplicationHomeController {
	
	// Reference to the main application.
    private MainApp mainApp;
    
    @FXML
    private MenuItem encryptHomeMenuBtn, homeMenuBtn, exitMenuBtn;
    
	/**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
	public ApplicationHomeController() {
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
    }
}
