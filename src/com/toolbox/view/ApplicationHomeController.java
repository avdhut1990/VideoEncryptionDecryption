package com.toolbox.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.toolbox.MainApp;
import com.toolbox.model.EncryptDecryptVideo;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ApplicationHomeController {

	// Reference to the main application.
    private MainApp mainApp;
    
    @FXML
    private Button videoFileBtn, encKeyFileBtn, playBtn, stopBtn, fullScreenBtn;
    
    @FXML
    private TextField videoFileText, encKeyFileText;
    
    @FXML
    private MediaView playerView;
    
    private File encVideoFile, encFile, decryptedFile;
	private Alert alert = new Alert(AlertType.ERROR);
	private Media m;
	private MediaPlayer mp;
	

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

        videoFileBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//Select video file
        		FileChooser fileChooser = new FileChooser();
        		fileChooser.setTitle("Select video file");
        		fileChooser.getExtensionFilters().addAll(
        			new ExtensionFilter("Encrypted Video Files", "*.ddd"));
        		encVideoFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        		if (encVideoFile != null) {
        			videoFileText.setText(encVideoFile.toString());
        		}
            }
        });

        encKeyFileBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//Select encryption file
        		FileChooser fileChooser = new FileChooser();
        		fileChooser.setTitle("Select encryption file");
        		fileChooser.getExtensionFilters().addAll(
        			new ExtensionFilter("Encryption Files", "*.key"));
        		encFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        		if (encFile != null) {
        			encKeyFileText.setText(encFile.toString());
        		}
            }
        });

        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (mp == null) {
	            	if (videoFileText.getText().isEmpty()) {
	            		alert.setTitle("Error");
	            		alert.setHeaderText(null);
	            		alert.setContentText("Please select video file!");
	            		alert.showAndWait();
	            	}
	            	else if (encKeyFileText.getText().isEmpty()) {
	            		alert.setTitle("Error");
	            		alert.setHeaderText(null);
	            		alert.setContentText("Please select encryption file!");
	            		alert.showAndWait();
	            	}
	            	else {
	        	        StringBuilder encodedKey = new StringBuilder();
	            	    try {
	                		BufferedReader br = new BufferedReader(new FileReader(encFile));
	            	        String line = br.readLine();
	
	            	        while (line != null) {
	            	        	encodedKey.append(line);
	            	            line = br.readLine();
	            	        }
	            	        br.close();
	            	        
	                		byte[] decodedKey = Base64.getDecoder().decode(encodedKey.toString());
	                		// rebuild key using SecretKeySpec
	                		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
	                		
	                		EncryptDecryptVideo edv = new EncryptDecryptVideo();
	                		decryptedFile = edv.decryptVideoFile(encVideoFile, originalKey);
	                		
	                		playVideo(decryptedFile);
	            	    } catch (Exception e) {
	            	    	e.printStackTrace();
	            	    }
	            	}
	            	playBtn.setText("Pause");
            	}
            	else {
            		if (mp.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            			mp.pause();
	            		playBtn.setText("Play");
            		}
            		else if (mp.getStatus().equals(MediaPlayer.Status.PAUSED) || mp.getStatus().equals(MediaPlayer.Status.STOPPED)) {
	            		mp.play();
	            		playBtn.setText("Pause");
            		}
            	}
            }
        });

        
        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (mp == null) {
            		if (videoFileText.getText().isEmpty()) {
	            		alert.setTitle("Error");
	            		alert.setHeaderText(null);
	            		alert.setContentText("Please select video file!");
	            		alert.showAndWait();
	            	}
	            	else if (encKeyFileText.getText().isEmpty()) {
	            		alert.setTitle("Error");
	            		alert.setHeaderText(null);
	            		alert.setContentText("Please select encryption file!");
	            		alert.showAndWait();
	            	}
            	}
            	else {
                	mp.stop();
                	playBtn.setText("Play");
            	}
        	}
        });
        
        
        /*fullScreenBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if (decryptedFile != null) {
            		FullScreenPlayer fp = new FullScreenPlayer(decryptedFile);
            		fp.fullMP.setStartTime(mp.getCurrentTime());
            		fp.fullMP.play();
            		fp.setOnCloseRequest(new EventHandler<WindowEvent>() {
        	            @Override
        	            public void handle(WindowEvent event) {
        	            	mp.setStartTime(fp.fullMP.getCurrentTime());
        	            }
            		});
            		
            	}
            }
        });*/
    }
    
    private void playVideo(File videoFile) {
    	m = new Media(videoFile.toURI().toString());
		mp = new MediaPlayer(m);
		playerView.setMediaPlayer(mp);

		playerView.setPreserveRatio(true);
		
		this.mainApp.getPrimaryStage().show();
		mp.play();
		
        mp.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
            	playBtn.setText("Play");
            }
        });		
	}
    
    public class FullScreenPlayer extends Stage {
    	public Duration playbackProgress;
    	public MediaPlayer fullMP;
    	public Media fullMedia;
    	public MediaView fullMV;
    	
    	FullScreenPlayer(File videoFile){
    		fullMedia = new Media(videoFile.toURI().toString());
    		fullMP = new MediaPlayer(fullMedia);
    		fullMV = new MediaView(fullMP);
    		
    		DoubleProperty width = fullMV.fitWidthProperty();
    		DoubleProperty height = fullMV.fitHeightProperty();		
    		
    		fullMV.setPreserveRatio(true);
    		
    		StackPane root = new StackPane();
    		root.getChildren().add(fullMV);
    		
    		final Scene scene = new Scene(root, 960, 540);
    		scene.setFill(Color.BLACK);
    		
    		this.setScene(scene);

    		width.bind(Bindings.selectDouble(fullMV.sceneProperty(), "width"));
    		height.bind(Bindings.selectDouble(fullMV.sceneProperty(), "height"));

    		this.setTitle("Full Screen Video Player");
    		this.setFullScreen(true);
    		this.show();
    	}
    }
}
