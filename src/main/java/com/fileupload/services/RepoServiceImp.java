package com.fileupload.services;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fileupload.dao.FileDAO;

@Service("repoService")
public class RepoServiceImp implements RepoService, Serializable{

	@Autowired
	private FileDAO fileDAO;
	
	private static final long serialVersionUID = 1L;

	@Override
	public FileMetaData save(FileData fileData) {
		getDAO().insert(fileData);
		return fileData.getMetaData();
	}

	@Override
	public List<FileMetaData> findFiles(String owner, String city) {
		return getDAO().searchByNameDate(owner, city);
	}
	public FileDAO getDAO() {
		return fileDAO;
	}

}
