package com.example.StreetvendorBackend.Modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestVendor {
	private String vendorcontact;
	private String vendorname;
	private String shopname;
	private String location;
	private String latitude;
	private String longitude;
	private String notificationToken;
	private String password;
}
