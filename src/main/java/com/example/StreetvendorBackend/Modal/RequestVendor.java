package com.example.StreetvendorBackend.Modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestVendor {
	private long vendorcontact;
	private String vendorname;
	private String shopname;
	private String location;
	private double latitude;
	private double longitude;
	private String notificationToken;
}
