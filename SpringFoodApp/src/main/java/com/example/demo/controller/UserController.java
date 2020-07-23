package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.files.upload.message.ResponseMessage;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.CartProduct;
import com.example.demo.model.CartwithDish;
import com.example.demo.model.Dish;
import com.example.demo.model.Feedback;
import com.example.demo.model.Order;
import com.example.demo.model.Payment;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.DishRepository;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.repository.OrderRepository;
<<<<<<< HEAD
import com.example.demo.repository.PaymentRepository;
=======
>>>>>>> 045efe1ee2793e80a349cfe55b1d232c208d944e

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
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
    @Autowired
    private MongoTemplate mongoTemplate;
	
	@PostMapping("/createcart")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
		if(cart.getCartItems().size()<1)
		{
			return ResponseEntity.badRequest().build();
		}
		//System.out.println("create cart called");

		String currentUserName=SecurityContextHolder.getContext().getAuthentication().getName();
		List<CartProduct> cartnewProducts=cart.getCartItems();
		cart.setCartItems(cartnewProducts);
		
		if(cartRepository.existsById(currentUserName))
		{
			
			CartProduct cartProductTemp;
			ArrayList<CartProduct> cartToaddProducts=(ArrayList<CartProduct>) cart.getCartItems();
			String restaurantName=dishRepository.findById(cartToaddProducts.get(0).getId()).get().getRestaurantName();
			if(restaurantName.equalsIgnoreCase(dishRepository.findById(cartnewProducts.get(0).getId()).get().getRestaurantName()))
			{

				cartToaddProducts.addAll(cartRepository.findById(currentUserName).get().getCartItems());
				ArrayList<CartProduct> cartproductarray=new ArrayList<CartProduct>();
				for(int i=0;i<cartToaddProducts.size();i++)
				{
					if(cartToaddProducts.get(i).getQuantity()>0)
					{
						cartProductTemp=cartToaddProducts.get(i);
						String cartProductTempIdString=cartProductTemp.getId();
						Long quantiLong=(long) 0;
						for(int j=i+1;j<cartToaddProducts.size();j++)
						{
							if(cartToaddProducts.get(j).getQuantity()>0 && cartToaddProducts.get(j).getId().equals(cartProductTempIdString))
							{
								quantiLong+=cartToaddProducts.get(j).getQuantity();
								cartToaddProducts.get(j).setQuantity((long) 0);
							}
						}
						cartProductTemp.setQuantity(cartToaddProducts.get(i).getQuantity()+quantiLong);
						cartproductarray.add(cartProductTemp);
					}
				}
				cart.setCartItems(cartproductarray);
				cartRepository.deleteById(currentUserName);

			}
			else 
			{
				return ResponseEntity.badRequest().build();
			}
		}
		cart.setId(currentUserName);

		Date date = new Date();
		cart.setDateCreated(date);
		cart.setStatus("Received");
		Float totalCost=new Float(0);
		
		
		for (CartProduct cartProduct: cart.getCartItems())
		{
			totalCost+=dishRepository.findById(cartProduct.getId()).get().getPrice()*cartProduct.getQuantity();	
		}

		cart.setTotalCost(totalCost);
		cartRepository.save(cart);
		//System.out.println("Successfully added cart for username "+currentUserName+" cart " + cart);
		return ResponseEntity.ok().body(cart);		
    }
	@GetMapping("/getcart")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CartwithDish> getCart() {
		//System.out.println("get cart called");
		try {
			String currentUserName=SecurityContextHolder.getContext().getAuthentication().getName();
			Cart cart = cartRepository.findById(currentUserName).get();
			CartwithDish cartwithDish = new CartwithDish();
			cartwithDish.setDateCreated(cart.getDateCreated());
			cartwithDish.setId(cart.getId());
			cartwithDish.setStatus(cart.getStatus());
			cartwithDish.setTotalCost(cart.getTotalCost());
			List<CartProduct> cartProducts = cart.getCartItems();
			List<CartItem> cartItems = new ArrayList<>();
			for (CartProduct cartProduct:cartProducts)
			{
				Dish dish = dishRepository.findById(cartProduct.getId()).orElseGet(() -> new Dish());
				Long quantity= cartProduct.getQuantity();
				cartItems.add(new CartItem(dish,quantity));
			}
			cartwithDish.setCartItems(cartItems);
			return ResponseEntity.ok().body(cartwithDish);	
		} catch (Exception e) {
			System.out.println("User not found: "+e.toString());
			return ResponseEntity.notFound().build();
		}	
    }
	@RequestMapping(value = "/deletecart",method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseMessage> deleteCart() {
		//System.out.println("delete cart called");
		String currentUserName=SecurityContextHolder.getContext().getAuthentication().getName();	
		return cartRepository.findById(currentUserName)
				.map(cart -> {
					cartRepository.deleteById(currentUserName);
					return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Cart deleted successfully"));
				}).orElse(ResponseEntity.notFound().build());
    }
	
	@PostMapping("/order")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Order> createOrder(@RequestBody Order orderdata) {
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		//cartRepository.deleteById(userName);
		System.out.println(orderdata);
		cartRepository.deleteById(userName);
		orderdata.setUserName(userName);
        Order insertedOrder =orderRepository.save(orderdata);
        return ResponseEntity.ok().body(insertedOrder); 
    }
	@PostMapping("/savefeedback")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ResponseMessage> createOrderFeedback(@RequestBody Feedback feedback) 
	{
		//System.out.println(feedback+"savefeedback called");
		//System.out.println("start "+feedback.getId() +"  "+feedback );
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		if(feedbackRepository.existsById(feedback.getId()))
		{
			return ResponseEntity.badRequest().body(new ResponseMessage("Feedback already exists for this order"));
		}
		try {
			Order order = orderRepository.findByIdAndUsername(feedback.getId(),username);
			Feedback newFeedback = new Feedback();
			
			newFeedback.setUsername(username);
			newFeedback.setId(feedback.getId());
			newFeedback.setRating(feedback.getRating());
			newFeedback.setComment(feedback.getComment());
			//System.out.println("sav newFeedback  "+newFeedback);
			feedbackRepository.save(newFeedback);	
			return ResponseEntity.ok().body(new ResponseMessage("Feedback added successfully"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseMessage("Order not found"));
		}
		//return ResponseEntity.ok().body(new ResponseMessage("Could not add feedback"));
	}
	
	@GetMapping(value = "/getallfeedbacks")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<ArrayList<Feedback>> getAllFeedbacks()
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			ArrayList<Feedback> feeds = new ArrayList<Feedback>();
			feeds = feedbackRepository.findByUsername(username);
			return ResponseEntity.ok().body(feeds);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	@GetMapping(value = "/getfeedback/{orderid}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Feedback> getFeedback(@PathVariable(value="orderid") String id) 
	{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
				return ResponseEntity.ok().body(feedbackRepository.findByIdAndUsername(id, username).get());
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value = "/getallorders")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<Order>> getAllOrders()
	{
		try {
		//System.out.println("getAllOrders called");
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		//System.out.println(orderRepository.findByUsername(userName));
		
		return ResponseEntity.ok().body(orderRepository.findByUsername(userName));
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Previous orders not found");
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/makePayment")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Payment> makePayment(@RequestBody Payment paymentdata) {
		System.out.println("make payment called");
		System.out.println(paymentdata);
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		paymentdata.setUsername(userName);
        Payment insertedPayment =paymentRepository.save(paymentdata);
        return ResponseEntity.ok().body(insertedPayment); 
    }
	
	@GetMapping(value = "/getPreviousPayment")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Payment> getPreviousPayment()
	{
		try {
		System.out.println("getPreviousPayment called");
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		//String userName="anonymousUser";
		Payment p =paymentRepository.findTopByUsername(userName,Sort.by("id").descending());
		//List<Payment> pr=paymentRepository.findByUsername(userName);
		System.out.println(p);
		return ResponseEntity.ok().body(p);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Previous payment not found"+e);
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(value = "/getorder/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Order> getOrder(@PathVariable(value = "id") String id)
	{
		//System.out.println("getOrder called");
		try {
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();

		//System.out.println("getorder  "+orderRepository.findByIdAndUsername(id, userName));
		return ResponseEntity.ok().body(orderRepository.findByIdAndUsername(id, userName));
		}
		catch (Exception e) {
			System.out.println("Order not found");
			return ResponseEntity.notFound().build();
		}
	}
	
}
