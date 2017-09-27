package com.fileupload.services;

import java.io.Serializable;
import java.util.Properties;

public class FileMetaData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String owner;
	private String city;
	public FileMetaData() {
		super();
	}
	public FileMetaData(String fileName, String owner, String city) {
		this.fileName = fileName;
		this.owner = owner;
		this.city = city;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Properties createProperties() {
        Properties props = new Properties();
        props.setProperty("file-name", getFileName());
        props.setProperty("owner-name", getOwner());
        props.setProperty("city", getCity());
        return props;
    }
	public FileMetaData(Properties properties) {
		this(properties.getProperty("file-name"), properties.getProperty("owner-name"), properties.getProperty("city"));
	}
}
