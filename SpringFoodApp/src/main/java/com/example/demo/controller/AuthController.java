package com.example.demo.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.files.upload.message.ResponseMessage;
import com.example.demo.model.ConfirmationToken;
import com.example.demo.model.ERole;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.CafeteriaRepository;
import com.example.demo.repository.ConfirmationTokenRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.service.EmailSenderService;
import com.example.demo.security.service.UserDetailsImpl;
import com.example.demo.sequence.SequenceGeneratorService;
import com.example.demo.ses.AmazonEmail;
import com.example.demo.ses.AmazonSESSample;
import com.example.demo.ses.SESProcessor;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	CafeteriaRepository cafeteriaRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Value("${foodapp.aws.instance.link}")
	private String ec2InstanceLink;
	
    // https://attacomsian.com/blog/amazon-ses-integration-with-spring-boot
    // https://docs.aws.amazon.com/ses/latest/DeveloperGuide/send-using-smtp-java.html
    // https://docs.aws.amazon.com/ses/latest/DeveloperGuide/send-email-simulator.html
	// https://docs.aws.amazon.com/ses/latest/DeveloperGuide/smtp-credentials.html
	// https://cloudacademy.com/blog/java-programming-how-to-send-emails-using-amazon-ses/
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		User currentUser=userRepository.findByUsername(loginRequest.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + loginRequest.getUsername()));
		if(userRepository.existsByUsername(loginRequest.getUsername()) ) {
			if(currentUser.isEnabled()==false) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
			            .body("Verify account first.");
			}
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		String confToken;
		try {
		System.out.println("sign up called");
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		System.out.println("valid username, email");
		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		System.out.println("user created");
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ROLE_ADMIN":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "ROLE_CAFETERIAMANAGER":
					Role cafeteriaRole = roleRepository.findByName(ERole.ROLE_CAFETERIAMANAGER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(cafeteriaRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
		System.out.println("user saved"+user.toString());
		//Long confirmationtoken= sequenceGeneratorService.generateSequence(ConfirmationToken.SEQUENCE_NAME);
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		confToken = confirmationToken.getConfirmationToken();
        confirmationTokenRepository.save(confirmationToken);

        // Using AmazonSESSample --------------------------------------------------
        AmazonSESSample amazonSESSample = new AmazonSESSample();
		amazonSESSample.sendmail("success@simulator.amazonses.com","Complete Registration by clicking on below link!","To confirm your account, please click here : "
		        +ec2InstanceLink+"/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());
		
        /*
        SESProcessor.getInstance().add(new AmazonEmail(
        signUpRequest.getEmail(),
        "Confirm your registeration",
        ec2InstanceLink+"/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken()));
        */
        /*
        System.out.println(mailSender);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom(mailSender);//enter your mail id
        mailMessage.setText("To confirm your account, please click here : "
        +"http://ec2-15-206-127-194.ap-south-1.compute.amazonaws.com/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);*/
        System.out.println("mail sent");
		}
		catch(Exception e)
		{
			return ResponseEntity.ok(new MessageResponse("User registered successfully! Email could not be sent!"));
		}
		return ResponseEntity.ok(new MessageResponse("User registered successfully! Please verify your email Id !" + ec2InstanceLink+"/api/auth/confirm-account?token="+confToken));
	}
	
	@GetMapping("/confirm-account")
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepository.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("successemailverification");
            confirmationTokenRepository.delete(token);
            
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
	
	@GetMapping("/forgot-password/{username}")
    public ResponseEntity<ResponseMessage> forgotUserPassword(@PathVariable("username") String username) {

		if(userRepository.existsByUsername(username) ) {
			if(userRepository.findOneUsername(username).isEnabled()==false) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
			            .body(new ResponseMessage("Verify account first."));
			}
		}
        User existingUser = userRepository.findOneUsername(username);
        System.out.println("forget pasword"+existingUser.toString());
        try {
            if (existingUser != null) {
                // Create token
                ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);

                // Save it
                confirmationTokenRepository.save(confirmationToken);
                
                // Create the email
                AmazonSESSample amazonSESSample = new AmazonSESSample();
        		amazonSESSample.sendmail("success@simulator.amazonses.com","Complete password reset!","Complete password reset, please click here : "
        		        +ec2InstanceLink+"/api/auth/confirm-reset?token="+confirmationToken.getConfirmationToken());
        		
                /*
                SESProcessor.getInstance().add(new AmazonEmail(
                		existingUser.getEmail(),
                        "Complete Password Reset!",
                        ec2InstanceLink+"/api/auth/confirm-reset?token="+confirmationToken.getConfirmationToken()));
                */
                /*
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(existingUser.getEmail());
                mailMessage.setSubject("Complete Password Reset!");
                mailMessage.setFrom(mailSender);
                mailMessage.setText("To complete the password reset process, please click here: "
                  + ec2InstanceLink+"/api/auth/confirm-reset?token="+confirmationToken.getConfirmationToken());
                
                // Send the email
                emailSenderService.sendEmail(mailMessage);
                */
                System.out.println("Mail to reset password sent");
                return ResponseEntity.ok().body(new ResponseMessage("Reset password link is sent to your registered email id."+ec2InstanceLink+"/api/auth/confirm-reset?token="+confirmationToken.getConfirmationToken()));

            } 			
		} catch (Exception e) {
	        return ResponseEntity.notFound().build();
		}
        return ResponseEntity.notFound().build();       
    }
	@GetMapping(value="/confirm-reset")
    public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token")String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            
            modelAndView.addObject("user", user);
            modelAndView.addObject("emailId", user.getEmail());
            modelAndView.setViewName("resetpassword");
            confirmationTokenRepository.delete(token);
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
	@PostMapping("/reset-password")
    public ModelAndView resetUserPassword(ModelAndView modelAndView, User user) {
        if (user.getEmail() != null) {
            // Use email to find user
            User tokenUser = userRepository.findByEmail(user.getEmail());
            tokenUser.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(tokenUser);
            modelAndView.addObject("message", "Password successfully reset. You can now log in with the new credentials.");
            modelAndView.setViewName("resetpasswordsuccessfull");
        } else {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}