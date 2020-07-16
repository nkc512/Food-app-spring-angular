package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.files.upload.message.ResponseMessage;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.CartProduct;
import com.example.demo.model.CartwithDish;
import com.example.demo.model.Dish;
import com.example.demo.model.Order;

import com.example.demo.repository.CartRepository;
import com.example.demo.repository.DishRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.security.service.UserDetailsImpl;

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
	
    @Autowired
    private MongoTemplate mongoTemplate;
	
	@PostMapping("/createcart")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
		if(cart.getCartItems().size()<1)
		{
			return ResponseEntity.badRequest().build();
		}
		System.out.println("create cart called");

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
		System.out.println("Successfully added cart for username "+currentUserName+" cart " + cart);
		return ResponseEntity.ok().body(cart);		
    }
	@GetMapping("/getcart")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CartwithDish> getCart() {
		System.out.println("get cart called");
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
		System.out.println("delete cart called");
		String currentUserName=SecurityContextHolder.getContext().getAuthentication().getName();	
		return cartRepository.findById(currentUserName)
				.map(cart -> {
					cartRepository.deleteById(currentUserName);
					return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Cart deleted successfully"));
				}).orElse(ResponseEntity.notFound().build());
    }
	
	@PostMapping("/order")
	@PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Order> createDish(@RequestBody Order orderdata) {
		String user_id=((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		orderdata.setUser_id(user_id);
        Order insertedOrder =orderRepository.save(orderdata);
        return ResponseEntity.ok().body(insertedOrder); 
    }
	@GetMapping(value = "/getallorders")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<Order>> getAllOrders()
	{
		try {
		System.out.println("getAllOrders called");
		String user_id=((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		System.out.println(orderRepository.findByUserid(user_id));
		
		return ResponseEntity.ok().body(orderRepository.findByUserid(user_id));
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Previous orders not found");
			return ResponseEntity.notFound().build();
		}
	}
	
}
