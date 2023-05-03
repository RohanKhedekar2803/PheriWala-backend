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
	private Long id;
	private Long vendorcontact;
	private String vendorname;
	private String shopname;
	private String location;
	private Double latitude;
	private  Double longitude;
	
}
