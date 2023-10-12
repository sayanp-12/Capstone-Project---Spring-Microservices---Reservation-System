package com.sayan.payments.controller;

import com.sayan.payments.model.Payment;
import com.sayan.payments.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
    PaymentRepository paymentRepository;

    @PostMapping
    public Payment makePayment(@RequestBody Payment payment) {
		return paymentRepository.save(payment);
    }

    @GetMapping("/{id}")
    public List<Payment> getPaymentById(@PathVariable Long id) {
		return paymentRepository.findByCustomerId(id);
    }
}
