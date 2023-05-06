package com.example.StreetvendorBackend.Service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.StreetvendorBackend.FirebaseMessagingService;
import com.example.StreetvendorBackend.Helper;
import com.example.StreetvendorBackend.NotificationMessage;
import com.example.StreetvendorBackend.Entity.Product;
import com.example.StreetvendorBackend.Entity.User;
import com.example.StreetvendorBackend.Entity.Vendor;
import com.example.StreetvendorBackend.Exception.ProductServiceException;
import com.example.StreetvendorBackend.Modal.LoginRequest;
import com.example.StreetvendorBackend.Modal.RequestFilter;
import com.example.StreetvendorBackend.Modal.RequestProduct;
import com.example.StreetvendorBackend.Modal.RequestUpdateLocation;
import com.example.StreetvendorBackend.Modal.RequestVendor;
import com.example.StreetvendorBackend.Modal.ResponseVendor;
import com.example.StreetvendorBackend.Modal.filtredVendorResponse;
import com.example.StreetvendorBackend.Repositrory.ProductRepository;
import com.example.StreetvendorBackend.Repositrory.UserRepository;
import com.example.StreetvendorBackend.Repositrory.VendorRepository;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class VendorServices {

	@Autowired
	private VendorRepository vendorrepository;
	
	@Autowired
	private ProductRepository productrepository;
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	FirebaseMessagingService firebasemessagingservice;
	
	public String RegisterVendor(RequestVendor requestvendor) {
		String username=requestvendor.getVendorname();
		Optional<Vendor> v=vendorrepository.findByVendorusername(username);
		Vendor vendor=Vendor.builder()
				.vendorusername(requestvendor.getVendorname())
				.vendorcontact(requestvendor.getVendorcontact())
				.latitude(Double.parseDouble(requestvendor.getLatitude()))
				.longitude(Double.parseDouble(requestvendor.getLongitude()))
				.location(requestvendor.getLocation().toLowerCase())
				.shopname(requestvendor.getShopname().toLowerCase())
				.notificationToken(requestvendor.getNotificationToken())
				.password(requestvendor.getPassword())
				.image(requestvendor.getImage())
				.build();
		if(v.isPresent()) {
			return "vendor with same username present already";
		}
		 ArrayList<Vendor> vendorlist = (ArrayList<Vendor>) vendorrepository.findAll();
			for(Vendor it :vendorlist) {
				if(it.getVendorcontact().equals(requestvendor.getVendorcontact())) {
					return "vendor with same contact number exists";
				}
			}
		
		
		log.info("registering vendor!!");
		
		vendorrepository.save(vendor);
		log.info("registered vendor!!");
		
		
		return  Long.toString(vendor.getId());
	}

	public Product addproduct(RequestProduct requestproduct , Long vendorid) {

		log.info("finding vendor");
		Vendor vendor=vendorrepository.findById(vendorid).orElseThrow( () -> new ProductServiceException("vendor id not found " ,"VENDOR_NOT_FOUND" ));
		log.info("found vendor");
		Product product= Product.builder()
				.productname(requestproduct.getProductname())
				.price(requestproduct.getPrice())
				.vendor(vendor)
				.build();
		
		Product p=productrepository.save(product);
		
		log.info("product created");
		
		
		Set<Product> s=vendor.getProducts();
		s.add(product);
		vendor.setProducts(s);
		log.info("vendor products updated");
		return p;
	}
	
	public ArrayList<Product> Getproductbyvendorid(Long vendor_id) {

		log.info("finding vendor....");
		Vendor vendor=vendorrepository.findById(vendor_id).orElseThrow( () -> new ProductServiceException("vendor id not found " ,"VENDOR_NOT_FOUND" ));
		log.info("found vendor");
		Set <Product> s1=productrepository.findAllByVendor(vendor);
		
		log.info("all products "+s1);
		ArrayList<Product> al=new ArrayList<>();
		
		for(Product it : s1){
			al.add(it);
		}
			
	
		log.info("returned products");
		return al;
	}

	public void Deleteproduct(Long product_id) {
		log.info("finding product");
		Product product=productrepository.findById(product_id).orElseThrow( () -> new ProductServiceException("product id not found " ,"PRODUCT_NOT_FOUND" ));
		log.info("found product");
		
		productrepository.deleteById((long) product.getProductid());
		
		
	}

	public Product upadteproduct(RequestProduct requestproduct, Long product_id) {
			
		log.info("finding product");
		Product product=productrepository.findById(product_id).orElseThrow( () -> new ProductServiceException("product id not found " ,"PRODUCT_NOT_FOUND" ));
		log.info("found product");
		
		product.setPrice(requestproduct.getPrice());
		product.setProductname(requestproduct.getProductname());
		log.info("new product"+ product);
		return productrepository.save(product);
	}
	
	public 	Vendor getVendorByVendorUsernameAndPassword(String vendorUsername, String password) {
		log.info("request for loginvendor" + vendorUsername +password);

			log.info(vendorUsername + "  " + password);  
			Optional<Vendor> optionalVendor = vendorrepository.findByVendorusernameAndPassword(vendorUsername, password);
		      if (optionalVendor.isPresent()) {
		    	  log.info("found ");
		          Vendor vendor = optionalVendor.get();
		          return vendor;
		      } else {
		    	  log.info("not found");
		          return null;
		      } 	
		}
	
	public Vendor changepassword(String vendorusername, String password) {
		Optional<Vendor> optionalVendor = vendorrepository.findByVendorusername(vendorusername);
		if (optionalVendor.isPresent()) {
	    	  log.info("found");
	    	  Vendor v=optionalVendor.get();
	    	  v.setPassword(password);
	    	  vendorrepository.save(v);
	          return v;
	      } else {
	    	  log.info("not found");
	          return null;
	      } 	
		
	}
	
	public String uploadImage(String path,MultipartFile file,String username)throws IOException {

			
			log.info("image upload started ");
			
			// get incoming file name
			String name=username + "." + "png";
			
			
			//make full path  
			String filepath=path +  File.separator + name;
			
			
			//create folder if not created
			File f=new File(path);
			if(!f.exists()) {
				log.info("making new file");
				f.mkdirs();
			}
			
			//copy file
			try {
				log.info("copying files done");
				Files.copy(file.getInputStream(), Paths.get(filepath));
			} catch (IOException e) {
				log.info("error while copying");
				e.printStackTrace();
				return name ;
			}
			
			return name;
		}

	public InputStream getImage(String path,String filename) throws FileNotFoundException {
		String fullpath=path +File.separator +filename;
		InputStream is=new 	FileInputStream(fullpath);
		return is;
	}

	public ArrayList<Vendor> getVendorDetails(Long userid) {
		User user=userrepository.findById(userid).orElseThrow( () -> new ProductServiceException("wrong userid " ,"USER_NOT_FOUND" ));
		ArrayList<Vendor> set=(ArrayList<Vendor>) vendorrepository.findAll();
		return set;
	}

	public ArrayList<filtredVendorResponse> getVendorDetailsbyfilter(Long userid, RequestFilter filter) {
		
		
		
		Helper help =new Helper();
		log.info("getting current user");
		//gettinmg current user
		User user=userrepository.findById(userid).orElseThrow( () -> new ProductServiceException("wrong userid " ,"USER_NOT_FOUND" ));
		
		log.info("getting all vendors");
		//get all vendors
		ArrayList<Vendor> vendors1=(ArrayList<Vendor>) vendorrepository.findAll();
		
		log.info("initialize response array");
		// response arraylist
		ArrayList<filtredVendorResponse> vendors = new ArrayList<>();
		
		log.info("populate vendor response");
		//populate vendors with distance from user
		for(Vendor it : vendors1) {
			//store data in response vector
			long id=it.getId();
			String vendorusername=it.getVendorusername();
			String shopname=it.getShopname();
			String location=it.getLocation();
			String contact =it.getVendorcontact();
			double latitude=it.getLatitude();
			double longitude=it.getLongitude();
			//copy data from vendors to response vendor\
			
			filtredVendorResponse rv =filtredVendorResponse
					.builder().id((int)id)
					.vendorusername(vendorusername)
					.shopname(shopname)
					.location(location)
					.vendorcontact(contact)
					.latitude(String.valueOf(latitude))
					.longitude(String.valueOf(longitude))
					.image(it.getImage())
					.build();
			
			double distnace=help.distance(it.getLatitude(), user.getLatitude(),it.getLongitude(),user.getLongitude(), 0.0, 0.0);
			rv.setDistancefromuser(distnace);
			vendors.add(rv);
		}
		
		ArrayList<filtredVendorResponse> filteredvendors = new ArrayList<>();
		
		log.info("applying filter");
		if(filter.getArea()!="") {
			log.info("area filter applying");
			for(filtredVendorResponse it : vendors) {
				
				if(it.getLocation().equals(filter.getArea())) {
					log.info("got entry");
					double vendor_latitude=Double.parseDouble(it.getLatitude());
					double vendor_longitude=Double.parseDouble(it.getLongitude());
					double distnace=help.distance(vendor_latitude, user.getLatitude(),vendor_longitude,user.getLongitude(), 0.0, 0.0);
					
					filteredvendors.add(it);
				}
			}
		}
		else if(filter.getShopname()!="") {
			log.info("shopname filter applying");
			for(filtredVendorResponse it : vendors) {
				if(it.getShopname().equals(filter.getShopname())) {
					filteredvendors.add(it);
				}
			}
		}
		else if(filter.getNearby()>0){
			log.info("nearby filter applying");
			long filterrange=filter.getNearby();
			
			
			for(filtredVendorResponse it : vendors) {
				double vendor_latitude=Double.parseDouble(it.getLatitude());
				double vendor_longitude=Double.parseDouble(it.getLongitude());
				double distnace=help.distance(vendor_latitude, user.getLatitude(),vendor_longitude,user.getLongitude(), 0.0, 0.0);
				if(filterrange>=distnace) {
					filteredvendors.add(it);
				}
			}
			
		}else {
			log.info("no filter applying");
			filteredvendors=vendors;
		}
		
		return filteredvendors;
	}
	
	public boolean updatelocation(long userid, RequestUpdateLocation updatedlocation) {
		Vendor v= vendorrepository.findById(userid).orElseThrow( () -> new ProductServiceException("user with this id  not found " ,"USER_NOT_FOUND" ));
		v.setLatitude(Double.parseDouble(updatedlocation.getLatitude()));
		v.setLongitude(Double.parseDouble(updatedlocation.getLongitude()));
		vendorrepository.save(v);
		return true;
	}

	public boolean notifynearby(long vendorid, NotificationMessage notificationmessage) {
		double filterrange =3000.00;
		
		Vendor currentvendor= vendorrepository.findById(vendorid).orElseThrow( () -> new ProductServiceException("user with this id  not found " ,"USER_NOT_FOUND" ));;
		
		Helper help =new Helper();
		
		ArrayList<User> TobeNotifiesUsers=new ArrayList<>();
		ArrayList<User> users=(ArrayList<User>) userrepository.findAll();
		for(User it : users) {
			
			double distnace=help.distance(it.getLatitude(), currentvendor.getLatitude(),it.getLongitude(),currentvendor.getLongitude(), 0.0, 0.0);
			if(filterrange>=distnace) {
				TobeNotifiesUsers.add(it);
			}
		}
		
//		NotificationMessage
		
		for(User it : TobeNotifiesUsers) {
			
			NotificationMessage message= NotificationMessage
					.builder()
					.title(notificationmessage.getTitle())
					.body(notificationmessage.getBody())
					.build();
			log.info("sending notification to nearby");
			try {
				String  b=firebasemessagingservice.sendNotificationbyToken(message,it.getNotificationToken());
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				log.info("error in snding notification to nearby");
				e.printStackTrace();
			}
		}
		
		return true;
	}

	
}
