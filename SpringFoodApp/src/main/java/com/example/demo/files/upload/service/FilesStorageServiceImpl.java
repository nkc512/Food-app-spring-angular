package com.example.demo.files.upload.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
@Service
public class FilesStorageServiceImpl implements FilesStorageService {
	//@Value("${springfood.fileLocation}")
	//private String location;
	@Value("${springfood.fileLocation}")
	private Path root;

  @Override
  public void init() {
    try {
      Files.createDirectory(root);
    } catch (IOException e) {
      System.out.println("Error: Could not initialize folder for upload!" + e.toString());
    }
  }

  @Override
  public void save(MultipartFile file) {
    try {
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
    } catch (Exception e) {
      System.out.println("Error: Could not store the file. Error: " + file.getName().toString() + e.toString());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        System.out.println("Error: Could not read the file!" +filename);
        return null;
      }
    } catch (MalformedURLException e) {
      System.out.println("Error: " + e.toString());
      return null;
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      System.out.println("Could not load the files!");
      return null;
    }
  }

}
