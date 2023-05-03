package com.example.StreetvendorBackend.Service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.StreetvendorBackend.Entity.User;
import com.example.StreetvendorBackend.Entity.Vendor;
import com.example.StreetvendorBackend.Exception.ProductServiceException;
import com.example.StreetvendorBackend.Modal.RequestUpdateLocation;
import com.example.StreetvendorBackend.Modal.RequestUser;
import com.example.StreetvendorBackend.Modal.RequestVendor;
import com.example.StreetvendorBackend.Modal.ResponseVendor;
import com.example.StreetvendorBackend.Repositrory.UserRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserServices {

	@Autowired
	private UserRepository userrepopository;
	
	public ResponseEntity<String> RegisterUser(RequestUser requestuser) {
		String username=requestuser.getUsername();
		Optional<User> v=userrepopository.findByUsername(username);
		if(v.isPresent()) {
			return new ResponseEntity<String>("user with same username exists", HttpStatus.NOT_ACCEPTABLE);
		}
			
		log.info("registering user!!");
		User user=User.builder()
				.latitude(requestuser.getLatitude())
				.longitude(requestuser.getLongitude())
				.notificationToken(requestuser.getNotificationToken())
				.usercontact(requestuser.getUsercontact())
				.username(username)
				.password(requestuser.getPassword())
				.notificationToken(requestuser.getNotificationToken())
				.build();
		
		userrepopository.save(user);
		log.info("registered user!!");
		
		return new ResponseEntity<String>("Done", HttpStatus.OK);
		
	}

	
	public ResponseEntity<User> getuserByUsernameAndPassword(String username, String password) {
		log.info(username + "  " + password);  
		Optional<User> optionaluser = userrepopository.findByUsernameAndPassword(username, password);
	      if (optionaluser.isPresent()) {
	    	  log.info("found ");
	          User user = optionaluser.get();
	          return ResponseEntity.ok(user);
	      } else {
	    	  log.info("not found");
	          return ResponseEntity.notFound().build();
	      } 	
	}


	public ResponseEntity<User> changepassword(String username, String password) {
		Optional<User> optionalUser = userrepopository.findByUsername(username);
		if (optionalUser.isPresent()) {
	    	  log.info("found");
	    	  User v=optionalUser.get();
	    	  v.setPassword(password);
	    	  userrepopository.save(v);
	          return ResponseEntity.ok(v);
	      } else {
	    	  log.info("not found");
	          return ResponseEntity.notFound().build();
	      } 	
	}


	public boolean updatelocation(long userid, RequestUpdateLocation updatedlocation) {
		User user= userrepopository.findById(userid).orElseThrow( () -> new ProductServiceException("user with this id  not found " ,"USER_NOT_FOUND" ));
		user.setLatitude(updatedlocation.getLatitude());
		user.setLongitude(updatedlocation.getLongitude());
		userrepopository.save(user);
		return true;
	}


 
	
}
