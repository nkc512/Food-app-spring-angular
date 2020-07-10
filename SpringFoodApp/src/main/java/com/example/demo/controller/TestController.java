package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Dish;
import com.example.demo.repository.CafeteriaRepository;
import com.example.demo.repository.DishRepository;
import com.example.demo.sequence.SequenceGeneratorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	CafeteriaRepository cafeteriaRepository;
	
	@Autowired
	DishRepository dishRepository;
	
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

	
	@GetMapping("/")
	public List<Dish> getAllDish() throws ResourceNotFoundException
	{
		System.out.println("reach getAllDish");
		return dishRepository.findAll();
	}
	
	@GetMapping("/cafeteria/{cafeteriaId}")
	public List<Dish> getCafeteriaDishs(@PathVariable(value = "cafeteriaId") String cafeterianame) throws ResourceNotFoundException
	{
		return dishRepository.findByCafeterianame(cafeterianame);
	}
	@GetMapping("/cafeteria/{cafeteriaId}/search/{searchval}")
	public List<Dish> getCafeteriaDishFind(@PathVariable(value = "cafeteriaId") String cafeterianame,@PathVariable(value="searchval") String searchval) throws ResourceNotFoundException
	{
		return dishRepository.findByDishnameContainingAndCafeterianame(searchval, cafeterianame);
	}	
	@GetMapping("/dish/{searchval}")
	public List<Dish> getDish(@PathVariable(value="searchval") String searchval) throws ResourceNotFoundException
	{
		return dishRepository.findByDishnameContaining(searchval);
	}
	@PostMapping("/cafeteria/add/{cafeteriausername}")
	@PreAuthorize("hasRole('CAFETERIAMANAGER')")
	public Dish createDish(@PathVariable(value = "cafeteriausername") String cafeteriausername,
			@Valid @RequestBody Dish food) throws ResourceNotFoundException
	{
		Dish dish =new Dish();
		dish.setId(sequenceGeneratorService.generateSequence(Dish.SEQUENCE_NAME));
		
		dish.setAvailability(food.getAvailability());
		dish.setDishname(food.getDishname());
		dish.setCafeterianame(cafeteriausername);
		dish.setCategory(food.getCategory());
		dish.setDescription(food.getDescription());
		dish.setPrice(food.getPrice());
		dish.setVegNonveg(food.getVegNonveg());
		dishRepository.save(dish);
		System.out.println(dish);
		return dish;
	}

}