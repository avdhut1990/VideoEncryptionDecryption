package com.toolbox.model;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SourceData {
	private final StringProperty fileSerial;
	private final File file;
	
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
     * @param file
     */
    public SourceData(String fileSerial, File file) {
        this.fileSerial = new SimpleStringProperty(fileSerial);
        this.file = file;
    }

    public StringProperty getFileSerial() {
		return fileSerial;
	}

	public File getFile() {
		return file;
	}
}
