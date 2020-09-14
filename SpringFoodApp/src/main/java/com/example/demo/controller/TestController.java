package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.files.upload.message.ResponseMessage;
import com.example.demo.repository.CafeteriaRepository;
import com.example.demo.repository.DishRepository;
import com.example.demo.s3files.S3Services;
import com.example.demo.sequence.SequenceGeneratorService;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCursor;
import com.example.demo.model.ContactUs;
import com.example.demo.model.Dish;;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	@Autowired
	CafeteriaRepository cafeteriaRepository;
	
	@Autowired
    DishRepository dishRepository;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	  @Autowired
	  S3Services s3Services;
	  
	    @PostMapping("/upload")
	    @PreAuthorize("hasRole('ROLE_CAFETERIAMANAGER') or hasRole('ADMIN')")
	    public String uploadMultipartFile(@RequestParam("file") MultipartFile file) {
	    	try {

	  	      String keyName = file.getOriginalFilename();
	  	    s3Services.uploadFile(keyName, file);
	  	    return "Upload Successfully -> KeyName = " + keyName;	
			} catch (Exception e) {
				System.out.println("Upload error "+e.toString());
			}
	    	return "Could not upload file in S3";
	    }    
	    @GetMapping("/{keyname:.+}")
	    public ResponseEntity<byte[]> downloadFile(@PathVariable String keyname) {
			System.out.println("Get file called" + keyname);
	    	try {

	  	      ByteArrayOutputStream downloadInputStream = s3Services.downloadFile(keyname);
	  	    System.out.println("Get file called downloadfile " + keyname);
	  	      return ResponseEntity.ok()
	  	            .contentType(contentType(keyname))
	  	            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + keyname + "\"")
	  	            .body(downloadInputStream.toByteArray());  	
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Exception called. Could not fetch file "+keyname + e.toString());
			}
	    	return ResponseEntity.notFound().build();
	    }

	    @GetMapping("/allfiles")
	    public List<String> listAllFiles(){
	    	try {
	  	      return s3Services.listFiles();				
			} catch (Exception e) {
				System.out.println("Could not fetch list of files " + e.toString());
			}
	    	return null;
	    }
	    @GetMapping("/allfiles1")
	    public String files(){

	    	return new String("Hi");
	    }
	    
	    
	    private MediaType contentType(String keyname) {
	      String[] arr = keyname.split("\\.");
	      String type = arr[arr.length-1];
	      switch(type) {
	        case "txt": return MediaType.TEXT_PLAIN;
	        case "png": return MediaType.IMAGE_PNG;
	        case "jpg": return MediaType.IMAGE_JPEG;
	        default: return MediaType.APPLICATION_OCTET_STREAM;
	      }
	    }
	    
	@GetMapping("/all")
	public String allAccess() {
		
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('ROLE_CAFETERIAMANAGER') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}
	
    @GetMapping("/getRestaurantDishes/{restaurantname}")
    public ResponseEntity<List<Dish>> getRestaurantDishes(@PathVariable("restaurantname") String restaurantname) {
    	System.out.println(dishRepository.findRstaurantDishes(restaurantname));
    	
        return ResponseEntity.ok().body(dishRepository.findRstaurantDishes(restaurantname));
        
    }
    
    @GetMapping("/allRestaurants")
    public List<String> getRestaurants(){
    	List<String> restaurantList = new ArrayList<>();
    	DistinctIterable distinctIterable = mongoTemplate.getCollection("dish").distinct("restaurantName", String.class);
    	MongoCursor cursor = distinctIterable.iterator();
        while (cursor.hasNext()) {
            String category = (String)cursor.next();
            restaurantList.add(category);
        }
        
        return  restaurantList;
    	
    }

    @GetMapping("/hi")
	public String hifun()
	{
		return new String("Hi everyone");
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
	@GetMapping("/getDishes/{searchval}/restaurant/{restaurantName}")
	public ArrayList<Dish> getCafeteriaDishFind(@PathVariable(value = "restaurantName") String restaurantName,@PathVariable(value="searchval") String searchval) throws ResourceNotFoundException
	{
		System.out.println("Search val"+searchval+"  "+restaurantName);
		System.out.println(dishRepository.findBydishnamecontainingAndRestaurantName(searchval, restaurantName));
		return dishRepository.findBydishnamecontainingAndRestaurantName(searchval, restaurantName);
	}	
	@GetMapping("/getDishes/{searchval}")
	public ArrayList<Dish> getDish(@PathVariable(value="searchval") String searchval) throws ResourceNotFoundException
	{
		System.out.println("searchval  "+searchval);
		System.out.println(dishRepository.findBydishnamecontaining(searchval));
		return dishRepository.findBydishnamecontaining(searchval);
	}
	
	@PostMapping("/contactUs")
	public ResponseEntity<ResponseMessage> createcontactUs(@RequestBody ContactUs contactUs)
	{
		System.out.println("contact us called");
		return ResponseEntity.ok().body(new ResponseMessage(new String("Thanks for your comments. Our team will contact you soon.")));
	}
}