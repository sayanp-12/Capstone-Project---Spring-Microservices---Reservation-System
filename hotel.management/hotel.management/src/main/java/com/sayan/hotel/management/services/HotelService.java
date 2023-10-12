package com.sayan.hotel.management.services;

import com.sayan.hotel.management.entities.Hotels;
import com.sayan.hotel.management.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

	@Autowired
	HotelRepository hotelRepository;
	
	public List<Hotels> getAllHotel(){
		return hotelRepository.findAll();
	}
	
	public Hotels addHotel(Hotels hotel) {
		return hotelRepository.save(hotel);
	}
	
	public Hotels getHotel(Long hotelId) {
		return hotelRepository.findById(hotelId).orElse(null);
	}
	
	public Hotels updateHotelBookingStatus(Long hotelId, String status) {
		Hotels hotel =  getHotel(hotelId);
		if(hotel!=null) {
			hotel.setStatus(status);
			return hotelRepository.save(hotel);
		} else {
			return hotel;
		}
	}
}
