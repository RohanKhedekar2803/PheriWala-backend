package com.example.StreetvendorBackend.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private long id;
	
	@Column(name="user_contact")
	private long usercontact;
	
	@Column(name="user_name")
	private String username;
	
	@Column(name="pass_word")
	private String password;
	
	@Column(name="user_latitude")
	private double latitude;
	
	@Column(name="user_longitude")
	private  double longitude;
	
	@Column(name="notification_token")
	private String notificationToken;
	
}
