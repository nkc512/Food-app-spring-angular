package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.CartProduct;
import com.example.demo.model.CartwithDish;
import com.example.demo.model.Dish;
import com.example.demo.model.Order;

import com.example.demo.repository.CartRepository;
import com.example.demo.repository.DishRepository;
import com.example.demo.repository.OrderRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private DishRepository dishRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@PostMapping("/createcart")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
		System.out.println("create cart called");

		String currentUserName=SecurityContextHolder.getContext().getAuthentication().getName();
		if(cartRepository.existsById(currentUserName))
		{
			cartRepository.deleteById(currentUserName);
		}
		
		Date date = new Date();
		cart.setDateCreated(date);
		cart.setStatus("Received");
		Float totalCost=new Float(0);
		
		
		for (CartProduct cartProduct: cart.getCartItems())
		{
			totalCost+=dishRepository.findById(cartProduct.getId()).get().getPrice()*cartProduct.getQuantity();	
		}

		cart.setTotalCost(totalCost);
		cart.setId(currentUserName);
		cartRepository.save(cart);
		System.out.println("Successfully added cart for username "+currentUserName+" cart " + cart);
		return ResponseEntity.ok().body(cart);		
    }
	@GetMapping("/getcart")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CartwithDish> getCart() {
		System.out.println("get cart called");
		try {
			String currentUserName=SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println("currentusername"+currentUserName);
			Cart cart = cartRepository.findById(currentUserName).get();
			System.out.println("userfound");
			CartwithDish cartwithDish = new CartwithDish();
			cartwithDish.setDateCreated(cart.getDateCreated());
			cartwithDish.setId(cart.getId());
			cartwithDish.setStatus(cart.getStatus());
			cartwithDish.setTotalCost(cart.getTotalCost());
			List<CartProduct> cartProducts = cart.getCartItems();
			List<CartItem> cartItems = new ArrayList<>();
			for (CartProduct cartProduct:cartProducts)
			{
				System.out.println(cartProduct);
				Dish dish = dishRepository.findById(cartProduct.getId()).orElseGet(() -> new Dish());
				Long quantity= cartProduct.getQuantity();
				System.out.println(dish);
				System.out.println(quantity);
				cartItems.add(new CartItem(dish,quantity));
			}
			cartwithDish.setCartItems(cartItems);
			System.out.println("to show in user side"+cartwithDish);
			return ResponseEntity.ok().body(cartwithDish);	
		} catch (Exception e) {
			System.out.println("not found called "+e.toString());
			e.printStackTrace();
			//System.out.println(e.printStackTrace());
			return ResponseEntity.notFound().build();
		}	
    }

	
	@PostMapping("/order")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Order> createDish(@RequestBody Order orderdata) {
		System.out.println("reach "+orderdata);
             Order insertedOrder =orderRepository.save(orderdata);
             System.out.println("order placed with id "+insertedOrder);
             return ResponseEntity.ok().body(insertedOrder); 
    }
	
}
