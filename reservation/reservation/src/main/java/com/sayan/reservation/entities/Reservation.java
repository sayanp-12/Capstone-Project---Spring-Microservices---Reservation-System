package com.sayan.reservation.entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "hotelId")
    private Long hotelId;

    @Column(name = "bookingAmount")
    private BigDecimal bookingAmount;

    @Column(name = "startDate")
    private String startDate;

    @Column(name = "endDate")
    private String endDate;

}