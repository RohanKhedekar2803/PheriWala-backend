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

	private String usercontact;
	private String username;
	private String password;
	private String latitude;
	private  String longitude;
	private String notificationToken;
}
