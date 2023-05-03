package com.example.StreetvendorBackend.Controller;



import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.StreetvendorBackend.Entity.User;
import com.example.StreetvendorBackend.Entity.Vendor;
import com.example.StreetvendorBackend.Exception.ProductServiceException;
import com.example.StreetvendorBackend.Modal.LoginRequest;
import com.example.StreetvendorBackend.Modal.RequestFilter;
import com.example.StreetvendorBackend.Modal.RequestUpdateLocation;
import com.example.StreetvendorBackend.Modal.RequestUser;
import com.example.StreetvendorBackend.Modal.RequestVendor;
import com.example.StreetvendorBackend.Modal.filtredVendorResponse;
import com.example.StreetvendorBackend.Repositrory.UserRepository;
import com.example.StreetvendorBackend.Service.UserServices;
import com.example.StreetvendorBackend.Service.VendorServices;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {
	//register , login Profile , Change Password , Home
	
	@Autowired
	private UserServices userservices;
	
	@Autowired
	private VendorServices vendorservices;
	
	@PostMapping("/register")
	public ResponseEntity<String> registervendor(@RequestBody RequestUser requestuser){
		ResponseEntity<String> s=userservices.RegisterUser(requestuser);		
		return s;
	}
	
	
	@PostMapping("/")
	public ResponseEntity<User> loginuser(@RequestBody LoginRequest req){
		 return userservices.getuserByUsernameAndPassword(req.getUsername(),req.getPassword());
    }
	
	@PutMapping("/changepassword")
	public  ResponseEntity<User> Changepassword(@RequestBody LoginRequest req) {
		return userservices.changepassword(req.getUsername(),req.getPassword());
	}	
	
	
	@GetMapping("/getvendors/{userid}")
	public ArrayList<Vendor> GetVendorDetails(@PathVariable Long userid) {
		ArrayList<Vendor> set=vendorservices.getVendorDetails(userid);
		return set;
	}
	
	
//	send filter as body in post check body of filter in modals keep only 1 filter active at a time keep by default values as "" for string and -1 for long type 
	@PostMapping("/getfilteredvendors/{userid}")
	public  ArrayList<filtredVendorResponse> GetVendorDetailsbyfilter(@PathVariable Long userid,@RequestBody RequestFilter filter) {
		 ArrayList<filtredVendorResponse> vendors=	vendorservices.getVendorDetailsbyfilter(userid,filter);
		return vendors;
	}
	
	@PutMapping("/updatelocation/{userid}")
	public boolean updateuserlocation(@PathVariable long userid,@RequestBody RequestUpdateLocation updatedlocation) {
		return userservices.updatelocation(userid,updatedlocation);
		
	}
	
}
