package com.fileupload.dao;

import java.util.List;
import com.fileupload.services.FileData;
import com.fileupload.services.FileMetaData;
/**
 * 
 * @author shaikh salman
 *
 */
public interface FileDAO {
  /*
   * Insert data into filesystem
   */
	void insert(FileData data);
	List<FileMetaData> searchByNameDate(String owner, String city);
}
