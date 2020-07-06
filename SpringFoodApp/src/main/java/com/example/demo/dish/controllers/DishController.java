package com.example.demo.dish.controllers;

import com.example.demo.dish.repositories.DishRepository;
import com.example.demo.files.upload.message.ResponseMessage;
import com.example.demo.dish.models.Dish;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dishdata")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class DishController {
	@Autowired
	DishRepository dishRepository;
	

    @PostMapping("/dishes")
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
    public ResponseEntity<ResponseMessage> deleteDish(@PathVariable("id") String id) {
        return dishRepository.findById(id)
                .map(dish -> {
                    dishRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("deleted successfully"));
                }).orElse(ResponseEntity.notFound().build());
    }

}
