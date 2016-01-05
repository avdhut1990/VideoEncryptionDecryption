package com.toolbox.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SourceData {
	private final StringProperty fileSerial;
	private final StringProperty fileName;
	
	/**
     * Default constructor.
     */
    public SourceData() {
    	this(null, null);
    }

	/**
     * Constructor with some initial data.
     * 
     * @param fileSerial
     * @param fileName
     */
    public SourceData(String fileSerial, String fileName) {
        this.fileSerial = new SimpleStringProperty(fileSerial);
        this.fileName = new SimpleStringProperty(fileName);
    }

    public StringProperty getFileSerial() {
		return fileSerial;
	}

	public StringProperty getFileName() {
		return fileName;
	}
}
