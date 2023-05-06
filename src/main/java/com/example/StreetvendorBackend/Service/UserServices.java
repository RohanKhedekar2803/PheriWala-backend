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
	
	public String RegisterUser(RequestUser requestuser) {

		log.info("registering" + requestuser);
		String username=requestuser.getUsername();
		Optional<User> v=userrepopository.findByUsername(username);
		if(v.isPresent()) {
			return "user with same username exists";
		}
			
		log.info("registering user!!");
		User user=User.builder()
				.latitude(Double.parseDouble(requestuser.getLatitude()))
				.longitude(Double.parseDouble(requestuser.getLongitude()))
				.notificationToken(requestuser.getNotificationToken())
				.usercontact(requestuser.getUsercontact())
				.username(username)
				.password(requestuser.getPassword())
				.notificationToken(requestuser.getNotificationToken())
				.build();
		
		
		User r=userrepopository.save(user);
		log.info("registered user!!");
		
		return Long.toString(r.getId());
		
	}

	
	public User getuserByUsernameAndPassword(String username, String password) {
		log.info(username + "  " + password);  
		Optional<User> optionaluser = userrepopository.findByUsernameAndPassword(username, password);
	      if (optionaluser.isPresent()) {
	    	  log.info("found ");
	          User user = optionaluser.get();
	          return user;
	      } else {
	    	  log.info("not found");
	          return null;
	      } 	
	}


	public User changepassword(String username, String password) {

		Optional<User> optionalUser = userrepopository.findByUsername(username);
		if (optionalUser.isPresent()) {
	    	  log.info("found");
	    	  User user=optionalUser.get();
	    	  user.setPassword(password);
	    	  userrepopository.save(user);
	          return user;
	      } else {
	    	  log.info("not found");
	    	  return null;
	      } 	
	}


	public boolean updatelocation(long userid, RequestUpdateLocation updatedlocation) {
		User user= userrepopository.findById(userid).orElseThrow( () -> new ProductServiceException("user with this id  not found " ,"USER_NOT_FOUND" ));
		user.setLatitude(Double.parseDouble(updatedlocation.getLatitude()));
		user.setLongitude(Double.parseDouble(updatedlocation.getLongitude()));
		userrepopository.save(user);
		return true;
	}


 
	
}
