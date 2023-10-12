package com.sayan.reservation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sayan.reservation.dto.Hotels;
import com.sayan.reservation.dto.Notification;
import com.sayan.reservation.dto.Payment;
import com.sayan.reservation.dto.ReservationRequest;
import com.sayan.reservation.entities.Reservation;
import com.sayan.reservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ApiGatewayClient apiGatewayClient;

    @Autowired
    private KafkaService kafkaService;

    @KafkaListener(topics = "${spring.kafka.topic.booking}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void customerNotify(String reservationPayload) throws Exception {
        log.info("message {}", reservationPayload);

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ReservationRequest reservationRequest = null;
        try {
            reservationRequest = mapper.readValue(reservationPayload, ReservationRequest.class);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (reservationRequest != null) {
            Hotels hotel = apiGatewayClient.getHotel(reservationRequest.getHotelId());
            if (hotel != null && "AVALIABLE".equalsIgnoreCase(hotel.getStatus())) {
                reservationRepository.save(Reservation.builder()
                                .hotelId(reservationRequest.getHotelId())
                                .customerId(reservationRequest.getCustomerId())
                                .bookingAmount(reservationRequest.getBookingAmount())
                                .startDate(reservationRequest.getStartDate())
                                .endDate(reservationRequest.getEndDate())
                        .build());
                Payment payment = new Payment();
                payment.setCustomerId(reservationRequest.getCustomerId());
                payment.setAmount(reservationRequest.getBookingAmount());
                apiGatewayClient.makePayment(payment);
                apiGatewayClient.updateHotelBookingStatus(hotel.getId(), "BOOKED");

                kafkaService.sendNotification(Notification.builder()
                        .customerId(reservationRequest.getCustomerId())
                        .eventType("Booking confirmed")
                        .message("Your Room is Booked in Hotel " + hotel.getName()
                                + " For Date : " + reservationRequest.getStartDate().toString()
                                + " To Date : " + reservationRequest.getEndDate().toString())
                        .build());
            } else {
                kafkaService.sendNotification(Notification.builder()
                        .customerId(reservationRequest.getCustomerId())
                        .eventType("Booking confirmed")
                        .message("Your Room is not available for Hotel " + hotel.getName()
                                + " For Date : " + reservationRequest.getStartDate().toString()
                                + " To Date : " + reservationRequest.getEndDate().toString())
                        .build());
            }
        } else {
            log.info("no message present");
        }
    }

}
