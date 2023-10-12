package com.sayan.payments.repository;

import com.sayan.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	List<Payment> findByCustomerId(Long customerId);
}
