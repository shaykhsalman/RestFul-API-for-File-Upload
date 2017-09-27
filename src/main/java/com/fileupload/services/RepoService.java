package com.fileupload.services;

import java.util.List;

public interface RepoService {
	FileMetaData save(FileData fileData);
	List<FileMetaData> findFiles(String owner, String City);
}
