package com.example.StreetvendorBackend.Modal;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestUser {

	private long usercontact;
	private String username;
	private String password;
	private double latitude;
	private  double longitude;
	private String notificationToken;
}
