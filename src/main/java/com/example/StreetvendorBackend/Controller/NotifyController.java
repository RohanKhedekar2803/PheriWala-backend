package com.example.StreetvendorBackend.Controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.StreetvendorBackend.FirebaseMessagingService;
import com.example.StreetvendorBackend.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/notify")
public class NotifyController {
	@Autowired
	FirebaseMessagingService firebasemessagingservice;
	
	@PostMapping("/send-notification")
	public String sendNotification(@RequestBody NotificationMessage notificationmessage ) throws FirebaseMessagingException {
		return firebasemessagingservice.sendNotificationbyToken(notificationmessage);
	}
	
	
}
