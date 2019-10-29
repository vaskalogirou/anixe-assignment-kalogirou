package com.kalogirou.anixe.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	@PostMapping("/hotels")
	public ResponseEntity<?> createHotel(@Valid @RequestBody Hotel hotel) {
		if (hotel.getId() != null) {
			return ResponseEntity.badRequest().body("A new hotel cannot already have an id");
		}
		Hotel result = hotelRepository.save(hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@GetMapping("/hotels")
	public List<Hotel> getAllHotels() {
		System.out.println("I got called!");
		List<Hotel> hotels = hotelRepository.findAll();
		return hotels;
	}
}
