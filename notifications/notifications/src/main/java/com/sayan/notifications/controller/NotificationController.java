package com.sayan.notifications.controller;

import com.sayan.notifications.entities.Notification;
import com.sayan.notifications..service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
    NotificationService notificationService;
	
    @PostMapping("/send")
    public String sendNotification() {
		return null;
    }
    
    @GetMapping("/customer/{customerId}")
    public List<Notification> getNotificationBycautomerId(@PathVariable Long customerId) {
		return notificationService.getAllNotifications(customerId);
    }
    
}
