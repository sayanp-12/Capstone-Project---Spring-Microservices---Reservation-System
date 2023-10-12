package com.sayan.customer.controller;

import com.sayan.customer.dto.ReservationRequest;
import com.sayan.customer.entity.Customers;
import com.sayan.customer.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @PostMapping("/register")
    public ResponseEntity<Object> createCustomer(@RequestBody Customers customers) throws Exception{
        return ResponseEntity.ok(customersService.createCustomer(customers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchCustomerById(@PathVariable(value = "id") Long customerId) throws Exception{
        return ResponseEntity.ok(customersService.fetchCustomerById(customerId));
    }

    @GetMapping("/")
    public ResponseEntity<Object> fetchAllCustomers() throws Exception{
        return ResponseEntity.ok(customersService.fetchAllCustomers());
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customers customers) throws Exception{
        return ResponseEntity.ok(customersService.updateCustomer(customers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable(value = "id") Long customerId) throws Exception{
        return ResponseEntity.ok(customersService.deleteCustomerById(customerId));
    }

    @PostMapping("/makereservation")
    public ResponseEntity<Object> makeReservasion(@RequestBody ReservationRequest reservation) throws Exception{
        return ResponseEntity.ok(customersService.makeReservation(reservation));
    }
}