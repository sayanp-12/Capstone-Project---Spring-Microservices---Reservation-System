package com.sayan.reservation.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {
    private Long id;
    
    private Long customerId;
    
    private BigDecimal amount;
    
}
