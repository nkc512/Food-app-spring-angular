package com.example.demo.files.upload.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.s3files.S3Services;
/*
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import com.example.demo.files.upload.message.ResponseMessage;
import com.example.demo.files.upload.model.FileInfo;
import com.example.demo.files.upload.service.FilesStorageService;
import java.util.stream.Collectors;
*/


// https://grokonez.com/frontend/angular/angular-6/angular-6-springboot-amazon-s3-upload-download-files-images-example
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/files")
public class FilesController {

	  @Autowired
	  S3Services s3Services;
	  
	    @PostMapping("/upload")
	    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER') or hasRole('ADMIN')")
	    public String uploadMultipartFile(@RequestParam("file") MultipartFile file) {
	    	try {

	  	      String keyName = file.getOriginalFilename();
	  	    s3Services.uploadFile(keyName, file);
	  	    return "Upload Successfully -> KeyName = " + keyName;	
			} catch (Exception e) {
				System.out.println("Upload error "+e.toString());
			}
	    	return "Could not upload file in S3";
	    }    
	    @GetMapping("/{keyname:.+}")
	    public ResponseEntity<byte[]> downloadFile(@PathVariable String keyname) {
			System.out.println("Get file called" + keyname);
	    	try {

	  	      ByteArrayOutputStream downloadInputStream = s3Services.downloadFile(keyname);
	  	    System.out.println("Get file called downloadfile " + keyname);
	  	      return ResponseEntity.ok()
	  	            .contentType(contentType(keyname))
	  	            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + keyname + "\"")
	  	            .body(downloadInputStream.toByteArray());  	
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Exception called. Could not fetch file "+keyname + e.toString());
			}
	    	return ResponseEntity.notFound().build();
	    }

	    @GetMapping("/all")
	    public List<String> listAllFiles(){
	    	try {
	  	      return s3Services.listFiles();				
			} catch (Exception e) {
				System.out.println("Could not fetch list of files " + e.toString());
			}
	    	return null;
	    }
	    
	    private MediaType contentType(String keyname) {
	      String[] arr = keyname.split("\\.");
	      String type = arr[arr.length-1];
	      switch(type) {
	        case "txt": return MediaType.TEXT_PLAIN;
	        case "png": return MediaType.IMAGE_PNG;
	        case "jpg": return MediaType.IMAGE_JPEG;
	        default: return MediaType.APPLICATION_OCTET_STREAM;
	      }
	    }
	    /*
  @Autowired
  FilesStorageService storageService;

  @PostMapping("/upload")
  @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER') or hasRole('ADMIN')")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.save(file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }
  
  @GetMapping("/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }
  

  @GetMapping("")
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

      return new FileInfo(filename, url);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }
  */
}

