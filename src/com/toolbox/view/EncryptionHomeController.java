package com.toolbox.view;

import java.io.File;
import java.util.List;

import com.toolbox.MainApp;
import com.toolbox.model.SourceData;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class EncryptionHomeController {
	
	// Reference to the main application.
    private MainApp mainApp;
    
    @FXML
    private Button srcBtn, encryptBtn, destBtn;
    
    @FXML
    private TextField destDir;
    
    @FXML
    private TableView<SourceData> fileTable;
    
    @FXML
    private TableColumn<SourceData, String> serialColumn;
    
    @FXML
    private TableColumn<SourceData, String> fileNameColumn;
	
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
	public EncryptionHomeController() {
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

        srcBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//Select source file
        		FileChooser fileChooser = new FileChooser();
        		fileChooser.setTitle("Select source files");
        		fileChooser.getExtensionFilters().addAll(
        			new ExtensionFilter("Video Files", "*.wmw", "*.avi", "*.mp4"));
        		List<File> tempList = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());
        		
        		for (File srcFile : tempList) {
					mainApp.addToSrcFileList(new SourceData("1", srcFile.toString()));
				}
        		System.out.println(mainApp.getSrcFileList().toString());
        		fileTable.setItems(mainApp.getSrcFileList());
            	serialColumn.setCellValueFactory(cellData -> cellData.getValue().getFileSerial());
            	fileNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFileName());
            }
        });
        
        destBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
        		//Select encryption destination directory
        		DirectoryChooser dirChooser = new DirectoryChooser();
        		dirChooser.setTitle("Select destination");
        		destDir.setText(dirChooser.showDialog(mainApp.getPrimaryStage()).toString());
            }
        });
    }
}
