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
	private String vendorcontact;
	private String vendorusername;
	private String shopname;
	private String location;
	private String latitude;
	private  String longitude;
	private double distancefromuser;
}
