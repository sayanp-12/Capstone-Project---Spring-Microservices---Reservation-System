package com.sayan.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    private Long customerId;

    private Long hotelId;

    private BigDecimal bookingAmount;

    private String startDate;

    private String endDate;

}