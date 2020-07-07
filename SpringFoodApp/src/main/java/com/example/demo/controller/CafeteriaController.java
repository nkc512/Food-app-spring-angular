package com.example.demo.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.example.demo.model.Dish;
import com.example.demo.files.upload.message.ResponseMessage;
import com.example.demo.repository.DishRepository;;
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
	
	@Autowired
    DishRepository dishRepository;
	
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
	
	@PostMapping("/dishes")
	@PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<Dish> createDish(@Valid @RequestBody Dish dish) {
    	 if(!dishRepository.dishAlreadyExist(dish.getDishName(),dish.getRestaurantName()).isEmpty())
    	 {
    		return ResponseEntity.notFound().build();
    	 }
    	 else {
             Dish insertedDish =dishRepository.save(dish);
             return ResponseEntity.ok().body(insertedDish);
    	 }
    }
    
    @GetMapping("/allDishes")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public List<Dish> getAllDishes() {
//        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
    	System.out.println(dishRepository.findAll());
        return dishRepository.findAll();
    }
    
    @PutMapping(value="/dishes/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable("id") String id,
                                           @Valid @RequestBody Dish updateDish) {
    	return dishRepository.findById(id).map(
    			dish->{
    				dish.setDishName(updateDish.getDishName());
    				dish.setPrice(updateDish.getPrice());
    				dish.setCategory(updateDish.getCategory());
    				dish.setDescription(updateDish.getDescription());
    				dish.setVegNonVeg(updateDish.getVegNonVeg());
    				dish.setAvailability(updateDish.isAvailability());
    				Dish updatedDish = dishRepository.save(dish);
                    return ResponseEntity.ok().body(updatedDish);
    			}).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping(value="/dishes/{id}")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<ResponseMessage> deleteDish(@PathVariable("id") String id) {
        return dishRepository.findById(id)
                .map(dish -> {
                    dishRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("deleted successfully"));
                }).orElse(ResponseEntity.notFound().build());
    }

}