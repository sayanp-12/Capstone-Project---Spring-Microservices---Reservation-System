package com.sayan.customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sayan.customer.dto.Hotels;
import com.sayan.customer.dto.Notification;
import com.sayan.customer.dto.ReservationRequest;
import com.sayan.customer.entity.Customers;
import com.sayan.customer.errorhandling.ResourceNotFoundException;
import com.sayan.customer.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private ApiGatewayClient apiGatewayClient;

    public Customers createCustomer(Customers customers) throws Exception {
        Optional<Customers> existCustomer = customersRepository.findByEmail(customers.getEmail());
        if (!existCustomer.isPresent()) {
            Customers createdCustomer = customersRepository.save(customers);
            if (Objects.nonNull(createdCustomer)) {
                kafkaService.sendNotification(Notification.builder()
                        .customerId(createdCustomer.getId())
                        .eventType("customer registered")
                        .message("Welcome To Hotel Reservation")
                        .build());
            }

            return createdCustomer;
        } else {
            throw new Exception("Customer with email " + customers.getEmail() + " is Already Exists.");
        }
    }

    public Customers fetchCustomerById(Long customerId) throws Exception {
        Optional<Customers> customersOptional = customersRepository.findById(customerId);
        if (customersOptional.isPresent()) {
            return customersOptional.get();
        } else {
            throw new ResourceNotFoundException("Customer Not Found");
        }
    }

    public List<Customers> fetchAllCustomers() {
        return customersRepository.findAll();
    }

    public Customers updateCustomer(Customers customers) throws Exception {
        Optional<Customers> customersOptional = customersRepository.findById(customers.getId());
        if (customersOptional.isPresent()) {
            return customersRepository.save(customers);
        } else {
            throw new ResourceNotFoundException("Customer is not Exists.");
        }
    }


    public String deleteCustomerById(Long customerId) {
        Optional<Customers> customersOptional = customersRepository.findById(customerId);
        if (customersOptional.isPresent()) {
            customersRepository.deleteById(customerId);
            return "Success";
        } else {
            throw new ResourceNotFoundException("Customer Not Found");
        }
    }


    public String makeReservation(ReservationRequest reservation) throws Exception {
        Optional<Customers> customerOp = customersRepository.findById(reservation.getCustomerId());
        if (customerOp.isPresent()) {
            Customers customer = customerOp.get();
            Hotels hotel = apiGatewayClient.getHotel(reservation.getHotelId());
            if (hotel != null && "AVAILABLE".equalsIgnoreCase(hotel.getStatus())) {
                ObjectMapper mapper = new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                ;
                String mess = null;
                try {
                    mess = mapper.writeValueAsString(reservation);
                } catch (JsonProcessingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                kafkaService.sendBookingNotification(ReservationRequest.builder()
                        .customerId(customer.getId())
                        .bookingAmount(reservation.getBookingAmount())
                        .startDate(reservation.getStartDate())
                        .endDate(reservation.getEndDate())
                        .hotelId(hotel.getId())
                        .build());
                return "Booking in progress";
            } else {

                kafkaService.sendNotification(Notification.builder()
                        .customerId(customer.getId())
                        .eventType("hotel")
                        .message("Currently Hotel information not present")
                        .build());
                return "Currently Hotel information not present";
            }
        } else {
            throw new ResourceNotFoundException("Customer is not Exists.");
        }
    }
}
