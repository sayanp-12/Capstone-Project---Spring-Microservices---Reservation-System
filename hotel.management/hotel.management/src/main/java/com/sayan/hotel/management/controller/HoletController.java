package com.sayan.hotel.management.controller;

import com.sayan.hotel.management.entities.Hotels;
import com.sayan.hotel.management.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/hotels")
public class HoletController {

	@Autowired
	HotelService hotelService;
	
	
	@GetMapping("/")
	public List<Hotels> getAllHotel(){
		return hotelService.getAllHotel();
	}
	
	@PostMapping("/addHotel")
	public Hotels addHotel(@RequestBody Hotels hotel) {
		return hotelService.addHotel(hotel);
		
	}
	@GetMapping("/{hotelId}")
	public Hotels getHotel(@PathVariable Long hotelId){
		return hotelService.getHotel(hotelId);
		
	}
	
	@GetMapping("/{hotelId}/bookingstatus/{status}")
	public Hotels updateHotelBookingStatus(@PathVariable Long hotelId,@PathVariable String status){
		return hotelService.updateHotelBookingStatus(hotelId,status);
	}
}
