package com.fileupload.services;

import java.io.Serializable;

public class FileData extends FileMetaData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private byte[] fileData;
	public FileData(byte[] fileData, String fileName, String owner, String city) {
		super(fileName, owner, city);
		this.fileData = fileData;
	}
	
	public FileMetaData getMetaData() {
		return new FileMetaData(getFileName(), getOwner(), getCity());
	}
	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	
	
}
