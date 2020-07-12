package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Dish;
import com.example.demo.model.order;
import com.example.demo.repository.OrderRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	OrderRepository orderRepository;
	
	@PostMapping("/order")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<order> createDish(@Valid @RequestBody order orderdata) {
             order insertedOrder =orderRepository.save(orderdata);
             return ResponseEntity.ok().body(insertedOrder); 
    }
}
