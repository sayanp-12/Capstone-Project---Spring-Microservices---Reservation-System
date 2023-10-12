package com.sayan.hotel.management.repository;

import com.sayan.hotel.management.entities.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelRepository extends JpaRepository<Hotels, Long>{

	
}
