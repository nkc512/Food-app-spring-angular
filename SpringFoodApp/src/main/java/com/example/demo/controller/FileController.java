package com.example.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/file")
public class FileController {

	  
	    @GetMapping("/hi")
	    public String hicheck(){
	    	System.out.println("Reach 1");
	    	return new String("Hi");
	    }

}
