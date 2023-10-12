package com.sayan.reservation.services;

import com.sayan.reservation.dto.Hotels;
import com.sayan.reservation.dto.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "apigateway-service", url = "http://localhost:8888", path = "/")
public interface ApiGatewayClient {

    @GetMapping("/hotels/{hotelId}/bookingstatus/{status}")
    public Hotels updateHotelBookingStatus(@PathVariable Long hotelId, @PathVariable String status);

    @GetMapping("/hotels/{hotelId}")
    public Hotels getHotel(@PathVariable Long hotelId);

    @PostMapping("/payments")
    public Payment makePayment(@RequestBody Payment payment);

}