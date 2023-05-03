package com.example.StreetvendorBackend;

import java.io.FileInputStream;




import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;


@SpringBootApplication
public class StreetvendorBackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(StreetvendorBackendApplication.class, args);
		
	}
	
}
