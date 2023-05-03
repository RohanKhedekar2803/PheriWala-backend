package com.example.StreetvendorBackend.Modal;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class filtredVendorResponse {
    private Long id;
	private Long vendorcontact;
	private String vendorusername;
	private String shopname;
	private String location;
	private double latitude;
	private  double longitude;
	private double distancefromuser;
}
