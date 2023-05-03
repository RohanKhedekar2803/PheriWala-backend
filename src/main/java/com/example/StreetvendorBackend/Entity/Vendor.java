package com.example.StreetvendorBackend.Entity;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="vendor_id")
	private Long id;
	
	@Column(name="vendor_contact")
	private Long vendorcontact;
	
	@Column(name="vendor_name")
	private String vendorusername;
	
	@Column(name="pass_word")
	private String password;
	
	@Column(name="shop_name")
	private String shopname;
	
	@Column(name ="vendor_location")
	private String location;
	
	@Column(name="vendor_latitude")
	private double latitude;
	
	@Column(name="vendor_longitude")
	private  double longitude;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<Product> products;
	
	@Column(name="notification_token")
	private String notificationToken;
	
	
		
}
