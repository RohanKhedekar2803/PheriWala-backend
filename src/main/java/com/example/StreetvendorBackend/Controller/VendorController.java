package com.example.StreetvendorBackend.Controller;


import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.StreetvendorBackend.NotificationMessage;
import com.example.StreetvendorBackend.Entity.Product;
import com.example.StreetvendorBackend.Entity.User;
import com.example.StreetvendorBackend.Entity.Vendor;
import com.example.StreetvendorBackend.Extras.FileResponse;
import com.example.StreetvendorBackend.Modal.LoginRequest;
import com.example.StreetvendorBackend.Modal.RequestProduct;
import com.example.StreetvendorBackend.Modal.RequestUpdateLocation;
import com.example.StreetvendorBackend.Modal.RequestVendor;
import com.example.StreetvendorBackend.Modal.ResponseVendor;
import com.example.StreetvendorBackend.Repositrory.VendorRepository;
import com.example.StreetvendorBackend.Service.VendorServices;

import lombok.extern.log4j.Log4j2;



@RestController
@RequestMapping("vendor")
@Log4j2
public class VendorController {
	
	//register , login Profile , Change Password , Home
	
	@Autowired
	private VendorServices vendorservice;
	
	@Autowired
	private VendorRepository vendorrepoisitory; 	
	
	@PostMapping("/register")
	public ResponseEntity<String> registervendor(@RequestBody RequestVendor requestvendor){

		
		ResponseEntity<String> s=vendorservice.RegisterVendor(requestvendor);
		
		return s;
	}
	
	@PostMapping("/addproduct/{vendorid}")
	public boolean addproduct(@RequestBody RequestProduct product,@PathVariable("vendorid") Long vendor_id) {
		boolean b=vendorservice.addproduct(product , vendor_id);
		
		return b;
	}
	
	@GetMapping("/getproducts/{vendorid}")
	public ResponseEntity<ArrayList<Product>> getproducts(@PathVariable("vendorid") Long vendor_id){
		ArrayList<Product> set = null;
		set=vendorservice.Getproductbyvendorid(vendor_id);
		return new ResponseEntity<>(set, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{productid}")
	public void deleteproduct(@PathVariable("productid") Long product_id){
		
		vendorservice.Deleteproduct(product_id);
		return ;
	}
	
	@PutMapping("/update/{productid}")
	public ResponseEntity<Product> updateproduct(
			@PathVariable("productid") Long product_id,
			@RequestBody RequestProduct product) {
		 Product p =vendorservice.upadteproduct(product,product_id);
		 return new ResponseEntity<>(p,HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Vendor> login(@RequestBody LoginRequest req){

		 return vendorservice.getVendorByVendorUsernameAndPassword(req.getUsername(),req.getPassword());
    }
	
	@PutMapping("/changepassword")
	public  ResponseEntity<Vendor> Changepassword(@RequestBody LoginRequest req) {
		return vendorservice.changepassword(req.getUsername(),req.getPassword());
		
	}
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/upload/{username}")
	public ResponseEntity<FileResponse> fileupload(@RequestParam("image") MultipartFile image ,
			@PathVariable String username) throws IOException {
		    
		    String filename;
			filename = vendorservice.uploadImage(path,image,username);
			return new ResponseEntity<FileResponse>(new FileResponse(filename,"img added"), HttpStatus.OK);
			
	}
	
	//serve file
		 @GetMapping("/download/{username}")
		 public void downloadprofilepic(@PathVariable String username, HttpServletResponse response) throws Exception{
			 log.info("COPYING ");
			 InputStream is =this.vendorservice.getImage(path, username +"."+"png");
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
			StreamUtils.copy(is, response.getOutputStream());
			
			 
			  
			    log.info("COPIED.. ");
			return ;
		 }
		 
		 @PutMapping("/updatelocation/{vendorid}")
			public boolean updateuserlocation(@PathVariable long vendorid,@RequestBody RequestUpdateLocation updatedlocation) {
				return vendorservice.updatelocation(vendorid,updatedlocation);
				
		}
		 
		 @PostMapping("notifynearby/{vendorid}")
			 public boolean notifynearby(@PathVariable long vendorid,@RequestBody NotificationMessage notificationmessage){
			 //get all in range and send notification
			 ArrayList<User> al=new ArrayList<>();
			 
				 return vendorservice.notifynearby(vendorid,notificationmessage);
		 }
	
}
