package com.example.StreetvendorBackend.Configurations;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class Config {
	

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        FirebaseApp firebaseApp = null;
        String appName = "pushnotification123";

        for (FirebaseApp app : FirebaseApp.getApps()) {
            if (app.getName().equals(appName)) {
                firebaseApp = app;
                break;
            }
        }

        if (firebaseApp == null) {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build();	
            firebaseApp = FirebaseApp.initializeApp(firebaseOptions, appName);
        }
        

        return FirebaseMessaging.getInstance(firebaseApp);
    }
  

    	
    
    
}
