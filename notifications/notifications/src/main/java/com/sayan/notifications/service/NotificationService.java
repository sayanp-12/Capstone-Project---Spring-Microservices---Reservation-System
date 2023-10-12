package com.sayan.notifications.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayan.notifications.entities.Notification;
import com.sayan.notifications.repositories.NotificationRepository;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class NotificationService {
	
	String className = this.getClass().getName();
	private final Logger log = LoggerFactory.getLogger(className);
	
    @Autowired
    NotificationRepository notificationRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void customerNotify(String notificationPayload) {
        log.info("message {}", notificationPayload);

        ObjectMapper mapper = new ObjectMapper();
        Notification notificationEvent = null;
        try {
            notificationEvent = mapper.readValue(notificationPayload, Notification.class);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (notificationEvent != null) {
            notificationEvent.setCreatedOn(new Date().toString());
            notificationRepository.save(notificationEvent);
        } else {
            log.info("no message present");
        }

    }

    public List<Notification> getAllNotifications(Long customerId) {
        return notificationRepository.findByCustomerId(customerId);
    }
}
