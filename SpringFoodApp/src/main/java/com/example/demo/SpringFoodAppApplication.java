package com.example.demo;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.demo.dish.repositories.DishRepository;
import com.example.demo.files.upload.service.FilesStorageService;
import com.example.demo.repository.UserRepository;

@ComponentScan("com.example.demo")
@EnableMongoRepositories(basePackageClasses
	    = {
	    		DishRepository.class,UserRepository.class
	    })
@SpringBootApplication
public class SpringFoodAppApplication implements CommandLineRunner{
	@Resource
	FilesStorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(SpringFoodAppApplication.class, args);
	}
	
	@Override
	  public void run(String... arg) throws Exception {
	    storageService.deleteAll();
	    storageService.init();
	  }

}
