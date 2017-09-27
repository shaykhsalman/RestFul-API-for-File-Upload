package com.fileupload.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.fileupload.services.FileData;
import com.fileupload.services.FileMetaData;
import com.fileupload.services.RepoService;


@Controller
public class FileController {

@Autowired
RepoService repoService;
	
	/**
	 * 
	 * @Startup Page Mapping.
	 * @returning index page.
	 */
	@RequestMapping("/")
	public String Home(Model model) {
		return "index";
	}
/**
 * 
 * @User uploads file, name and city.
 * @returns json data after storing data into filesystem.
 */
	@RequestMapping(value="/fileUploads", method=RequestMethod.POST)
	public @ResponseBody FileMetaData FileUpload(
			@RequestParam(value="file_upload", required = true) MultipartFile fileName,
			@RequestParam(value="owner", required=true) String owner,
			@RequestParam(value="city", required=true) String city) {
		try {
			FileData filedata = new FileData(fileName.getBytes(), fileName.getOriginalFilename(), owner, city);
			repoService.save(filedata);
			return filedata.getMetaData();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * @User requests files api by using GET method, passing owner and city.
	 * @returns json result.
	 */
	@RequestMapping(value = "/files", method = RequestMethod.GET)
    public HttpEntity<List<FileMetaData>> findDocument(
            @RequestParam(value="owner", required=false) String owner,
            @RequestParam(value="city", required=false) String city) {
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<List<FileMetaData>>(getRepoService().findFiles(owner, city), httpHeaders,HttpStatus.OK);
    }	
	
	public RepoService getRepoService() {
		return repoService;
	}
}
