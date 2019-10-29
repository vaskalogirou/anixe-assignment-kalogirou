package com.kalogirou.anixe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.repository.HotelRepository;

@RestController
@RequestMapping("/api")
public class HotelResource {
	private final HotelRepository hotelRepository;

	public HotelResource(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	@GetMapping("/hotels")
	public List<Hotel> getAllHotels() {
		System.out.println("I got called!");
		List<Hotel> hotels = hotelRepository.findAll();
		return hotels;
	}
}
