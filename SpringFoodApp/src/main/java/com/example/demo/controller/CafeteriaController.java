package com.example.demo.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Cafeteria;
import com.example.demo.model.User;
import com.example.demo.repository.CafeteriaRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.sequence.SequenceGeneratorService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cafeteria")
public class CafeteriaController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	CafeteriaRepository cafeteriaRepository;
	
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@PutMapping("update/{cafeteriaid}")
	@PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
	public Cafeteria updateCafeteria(@PathVariable(value="cafeteriaid") Long id, @Valid @RequestBody Cafeteria newcafeteria)
	{
		return cafeteriaRepository.findById(id)
				.map(cafeteria ->{
					cafeteria.setCafename(newcafeteria.getCafename());
				return cafeteriaRepository.save(cafeteria);
				})
				.orElseThrow(() -> new UsernameNotFoundException("Invalid User"));
				
		}	
	
//	@PostMapping("/add/{cafeteriaId}")
//	@PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
//	public Dish createDish(@PathVariable(value = "cafeteriaId") Long cafeteriaId,
//			@Valid @RequestBody Dish dish) throws ResourceNotFoundException
//	{
//		dish.setId(sequenceGeneratorService.generateSequence(Dish.SEQUENCE_NAME));
//		return cafeteriaRepository.findById(cafeteriaId).map(cafeteria -> {
//			dish.setRestaurantName(cafeteriaRepository.findById(cafeteriaId).orElse(new Cafeteria()).getCafename());
//			dish.setCafeteria(cafeteria);
//			return dishRepository.save(dish);
//		}).orElseThrow(() -> new ResourceNotFoundException("Cafeteria not found"));
//		
//	}
}
