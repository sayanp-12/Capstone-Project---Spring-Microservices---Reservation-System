package com.sayan.customer.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ReservationRequest {

    private Long customerId;

    private Long hotelId;

    private BigDecimal bookingAmount;

    private String startDate;

    private String endDate;

}