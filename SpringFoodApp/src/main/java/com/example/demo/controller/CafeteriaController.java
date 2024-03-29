package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.example.demo.model.Cafeteria;
import com.example.demo.model.Cart;
import com.example.demo.repository.CafeteriaRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.sequence.SequenceGeneratorService;
import com.example.demo.model.Dish;
import com.example.demo.model.Feedback;
import com.example.demo.model.Order;
import com.example.demo.files.upload.message.ResponseMessage;
import com.example.demo.repository.DishRepository;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.repository.OrderRepository;;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cafeteria")
public class CafeteriaController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	CafeteriaRepository cafeteriaRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
    DishRepository dishRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	FeedbackRepository feedbackRepository;
	
	@PutMapping("/update/{cafeteriaid}")
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
	
	@PostMapping("/dishes")
	@PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<Dish> createDish(@Valid @RequestBody Dish dish) {
		System.out.println("create dish called");
		String currentUserName=SecurityContextHolder.getContext().getAuthentication().getName();
		dish.setRestaurantName(currentUserName);
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
    	System.out.println(dishRepository.findAll());
        return dishRepository.findAll();
    }
    
    @PutMapping(value="/dishes/{id}")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
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
    
    
    @GetMapping("/getRestaurantDishes/{restaurantname}")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<List<Dish>> getRestaurantDishes(@PathVariable("restaurantname") String restaurantname) {
//        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
    	System.out.println(dishRepository.findRstaurantDishes(restaurantname));
    	
        return ResponseEntity.ok().body(dishRepository.findRstaurantDishes(restaurantname));
        
    }
    @GetMapping("/getorders")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<List<Cart>> getOrders()
    {
    	return ResponseEntity.ok().body(cartRepository.findAll());
    }
    @PostMapping("/serve")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<String> setOrderServed(@RequestBody String username)
    {
    	cartRepository.findById(username).get().setStatus("Served");
    	return ResponseEntity.ok().body(new String("Order successfull"));
    }

    @PutMapping(value="/changeStatus/{id}")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public Optional<Order> updateStatus(@PathVariable("id") String id, @RequestBody String status) {
    	Query select = Query.query(Criteria.where("order_id").is(id));
    	Update update = new Update();
    	update.set("status", status);
    	mongoTemplate.findAndModify(select, update, Order.class); 
    	return orderRepository.findById(id);
    }

    @GetMapping("/restWisePlacedOrder/{restname}")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<List<Order>> restWisePLacedPrders(@PathVariable("restname") String restname)
    {
    	System.out.println("placed order called");
    	return ResponseEntity.ok().body(orderRepository.restaurantWisePlacedOrders(restname));
    }
    
    @GetMapping("/restWiseAcceptedOrder/{restname}")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<List<Order>> restWiseAcceptedOrders(@PathVariable("restname") String restname)
    {
    	return ResponseEntity.ok().body(orderRepository.restaurantWiseAcceptedOrders(restname));
    }
    
    @GetMapping("/restWiseCookingOrder/{restname}")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<List<Order>> restWiseCookingOrders(@PathVariable("restname") String restname)
    {
    	return ResponseEntity.ok().body(orderRepository.restaurantWiseCookingOrders(restname));
    }
    
    @GetMapping("/restWiseReadyOrder/{restname}")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<List<Order>> restWiseReadyOrders(@PathVariable("restname") String restname)
    {
    	return ResponseEntity.ok().body(orderRepository.restaurantWiseReadyOrders(restname));
    }
    @GetMapping("/getfeedback")
    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER')")
    public ResponseEntity<ArrayList<Feedback>> getFeedback()
    {
    	String userName=SecurityContextHolder.getContext().getAuthentication().getName();
    	ArrayList<Feedback> feedbacks=(ArrayList<Feedback>) feedbackRepository.findAll();
    	ArrayList<Feedback> feedbackrestArrayList = new ArrayList<Feedback>();
    	try
    	{	
    		for(int i=0;i<feedbacks.size();i++)
    		{
	    		String id=feedbacks.get(i).getId();
	    		if(orderRepository.findById(id).get().getRestaurantName()==userName)
	    		{
	    			feedbackrestArrayList.add(feedbacks.get(i));
	    		}
    		}
    	}
    	catch (Exception e) {
			System.out.println(e);
		}
    	return ResponseEntity.ok().body(feedbacks);
    }
}