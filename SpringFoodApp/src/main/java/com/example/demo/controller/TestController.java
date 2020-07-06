package com.example.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CafeteriaRepository;
import com.example.demo.sequence.SequenceGeneratorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	CafeteriaRepository cafeteriaRepository;
	

	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('ROLE_CAFETERIAMANAGER') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	
//	@GetMapping("/")
//	public List<Dish> getAllDish() throws ResourceNotFoundException
//	{
//		return dishRepository.findAll();
//	}
//	
//	@GetMapping("/cafeteria/{cafeteriaId}")
//	public List<Dish> getCafeteriaDishs(@PathVariable(value = "cafeteriaId") Long cafeteriaId) throws ResourceNotFoundException
//	{
//		return dishRepository.findByCafeteriaId(cafeteriaId);
//	}
//	@GetMapping("/cafeteria/{cafeteriaId}/search/{searchval}")
//	public List<Dish> getCafeteriaDishFind(@PathVariable(value = "cafeteriaId") Long cafeteriaId,@PathVariable(value="searchval") String searchval) throws ResourceNotFoundException
//	{
//		return dishRepository.findByDishnameContainingAndCafeteriaId(searchval, cafeteriaId);
//	}	
//	@GetMapping("/dish/{searchval}")
//	public List<Dish> getDish(@PathVariable(value="searchval") String searchval) throws ResourceNotFoundException
//	{
//		return dishRepository.findByDishnameContaining(searchval);
//	}
	

}