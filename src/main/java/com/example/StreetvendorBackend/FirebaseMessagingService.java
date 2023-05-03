package com.example.StreetvendorBackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FirebaseMessagingService {
	
	private final FirebaseMessaging firebasemessaging;
	
	public FirebaseMessagingService(FirebaseMessaging firebasemessaging) {
		this.firebasemessaging=firebasemessaging;
	}
	
	public String sendNotificationbyToken(NotificationMessage notificationmessage) throws FirebaseMessagingException {
//		log.info("");
	

		
		
        Notification notification = Notification
                .builder()
                .setTitle(notificationmessage.getTitle())
                .setBody(notificationmessage.getBody())
                .setImage(notificationmessage.getBody())
                .build();

        Message message = Message
                .builder()
                .setToken(notificationmessage.getRecipientToken())
                .setNotification(notification)
                .putAllData(notificationmessage.getData())
                .build();

        try {
        	
        	
        	   firebasemessaging.send(message);
        	   return "succes in sending" ;
        }catch (FirebaseMessagingException e) {
			  e.printStackTrace();
			  return "failed in sending" ;
		}
      
    }
}
