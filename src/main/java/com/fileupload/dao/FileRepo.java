package com.fileupload.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.fileupload.services.FileData;
import com.fileupload.services.FileMetaData;

@Service("Filedao")
public class FileRepo implements FileDAO{
	public static final String DIRECTORY_NAME = "file_repo";
	
	@PostConstruct
	public void init() {
		createDirectory(DIRECTORY_NAME);
	}
	
	
	public String createDirectory(FileData fileData) {
		String path = getPath(fileData);
		createDirectory(path);
		return path;
	}
	
	public String getPath(FileData fileData) {
		return getPath(fileData.getFileName());
	}
	
	public String getPath(String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(DIRECTORY_NAME).append(File.separator).append(fileName);
		return sb.toString();
	}
	public void createDirectory(String path) {
		File file = new File(path);
		file.mkdirs();
	}


	@Override
	public void insert(FileData data) {
		try {
			createDirectory(data);
			saveFileData(data);
			saveMetaData(data);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}


	private void saveMetaData(FileData data) throws IOException{
		String path = getPath(data);
		Properties props = data.createProperties();
		File fis = new File(new File(path), "application.properties");
		FileOutputStream out = new FileOutputStream(fis);
		props.store(out, "File Meta Data");
		out.close();
	}


	private void saveFileData(FileData data) throws IOException{
		String path = getPath(data);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(new File(path), data.getFileName())));
		bos.write(data.getFileData());
		bos.close();
	}


	@Override
	public List<FileMetaData> searchByNameDate(String owner, String city) {
		try {
			return search(owner, city);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	public List<FileMetaData> search(String owner, String city) throws IOException{
		List<String> getData = allData();
		List<FileMetaData> mdlist = new ArrayList<FileMetaData>(getData.size());
		for(String item: getData) {
			FileMetaData fmd = getMetaDataFromDir(item);
			if(verify(fmd, owner, city)) {
				mdlist.add(fmd);
			}
		}
		return mdlist;
	}
	public FileMetaData getMetaDataFromDir(String item) throws IOException{
		FileMetaData fileDat = null;
		String path = getPath(item);
		File file = new File(path);
		if(file.exists()) {
			Properties properties = readProperties(item);
			fileDat = new FileMetaData(properties);
		}
		return fileDat;
	}
	private Properties readProperties(String item) throws IOException {
        Properties prop = new Properties();
        InputStream input = null;     
        try {
            input = new FileInputStream(new File(getPath(item), "application.properties"));
            prop.load(input);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
	public boolean verify(FileMetaData fmd, String owner, String city) {
		if(fmd == null) {
			return false;
		}
		boolean flag = true;
		if(owner!=null) {
			flag = (owner.equals(fmd.getOwner()));
		}
		if(flag && city!=null) {
			flag = (city.equals(fmd.getCity()));
		}
		return flag;
	}
	
	public List<String> allData(){
		File file = new File(DIRECTORY_NAME);
		String [] directories = file.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return new File(dir, name).isDirectory();
			}
		});
		return Arrays.asList(directories);
	}

}
