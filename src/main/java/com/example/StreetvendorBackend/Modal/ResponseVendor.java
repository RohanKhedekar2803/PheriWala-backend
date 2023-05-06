package com.example.StreetvendorBackend.Modal;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseVendor {
	private  int id;
	private String vendorcontact;
	private String vendorname;
	private String shopname;
	private String location;
	private String latitude;
	private  String longitude;
	
	
}
