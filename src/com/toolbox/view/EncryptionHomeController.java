package com.toolbox.view;

import java.io.File;
import java.io.FileWriter;
import java.util.Base64;
import java.util.List;

import com.toolbox.MainApp;
import com.toolbox.model.EncryptDecryptVideo;
import com.toolbox.model.SourceData;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class EncryptionHomeController {
	
	// Reference to the main application.
    @SuppressWarnings("unused")
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
    
    private Alert alert = new Alert(AlertType.ERROR);
	
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
        mainApp.generateEncKey();

        srcBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//Select source file
        		FileChooser fileChooser = new FileChooser();
        		fileChooser.setTitle("Select source files");
        		fileChooser.getExtensionFilters().addAll(
        			new ExtensionFilter("Video Files", "*.wmw", "*.avi", "*.mp4"));
        		List<File> tempList = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());
        		
        		if (tempList != null) {
	        		for (int i=0; i<tempList.size(); i++) {
						mainApp.addToSrcFileList(new SourceData(Integer.toString(i+1), tempList.get(i)));
					}
	
	        		fileTable.setItems(mainApp.getSrcFileList());
	            	serialColumn.setCellValueFactory(cellData -> cellData.getValue().getFileSerial());
	            	fileNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFile().toString()));
        		}
            }
        });
        
        destBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
        		//Select encryption destination directory
        		DirectoryChooser dirChooser = new DirectoryChooser();
        		dirChooser.setTitle("Select destination");
        		File dest = dirChooser.showDialog(mainApp.getPrimaryStage());
        		if (dest != null) {
        			destDir.setText(dest.toString());
        		}
            }
        });
        
        encryptBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (!destDir.getText().isEmpty()) {
	            	EncryptDecryptVideo edv = new EncryptDecryptVideo();
	    			for (SourceData src : mainApp.getSrcFileList()) {
	    				edv.encryptVideoFile(src.getFile(), destDir.getText(), mainApp.getSkey());
	    			}
	    			try {
	    				String encodedKey = Base64.getEncoder().encodeToString(mainApp.getSkey().getEncoded());
	    				File encKeyFile = new File(destDir.getText(), "enc.key");
	    				FileWriter fos = new FileWriter(encKeyFile);
	    				fos.write(encodedKey);
	    				fos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
	    			alert.setAlertType(AlertType.INFORMATION);
            		alert.setTitle("Success");
            		alert.setHeaderText(null);
            		alert.setContentText("All files encrypted successfully!");
            		alert.showAndWait();
            	}
            	else {
            		alert.setAlertType(AlertType.ERROR);
            		alert.setTitle("Error");
            		alert.setHeaderText(null);
            		alert.setContentText("Please select destination directory!");
            		alert.showAndWait();
            	}
            }
        });
    }
}
