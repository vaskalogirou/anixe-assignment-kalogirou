package com.kalogirou.anixe.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.repository.HotelRepository;
import com.kalogirou.anixe.service.HotelService;

@RestController
@RequestMapping("/api")
public class HotelController {
	private final HotelRepository hotelRepository;

	private final HotelService hotelService;

	public HotelController(HotelRepository hotelRepository, HotelService hotelService) {
		this.hotelRepository = hotelRepository;
		this.hotelService = hotelService;
	}

	@PostMapping("/hotels")
	public ResponseEntity<?> createHotel(@Valid @RequestBody Hotel hotel) {
		if (hotel.getId() != null) {
			return ResponseEntity.badRequest().body("A new hotel cannot already have an id");
		}
		hotelRepository.save(hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(hotel);
	}

	@GetMapping("/hotels")
	public List<Hotel> getAllHotels() {
		List<Hotel> hotels = hotelRepository.findAll();
		return hotels;
	}

	@GetMapping("/hotels/{id}")
	public ResponseEntity<?> getHotel(@PathVariable Long id) {
		Optional<Hotel> hotel = hotelRepository.findById(id);
		if (hotel.isPresent()) {
			return ResponseEntity.ok(hotel.get());
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/hotels/{id}")
	public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
		hotelRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/hotels")
	public ResponseEntity<?> updateHotel(@Valid @RequestBody Hotel hotel) {
		if (hotel.getId() == null) {
			return ResponseEntity.badRequest().body("Request to update a hotel should have a hotel id");
		}
		hotelRepository.save(hotel);
		return ResponseEntity.ok().body(hotel);
	}

	@GetMapping("/hotels/{id}/price-amount-sum-in-euro")
	public ResponseEntity<Float> getSumOfPriceAmountsByHotelId(@PathVariable Long id) {
		float sum = hotelService.calculateSumOfPriceAmountsInEuroByHotelId(id);
		return ResponseEntity.ok(sum);
	}

	@GetMapping("/hotels-for-surname/{customerSurname}")
	public ResponseEntity<List<Hotel>> getDistinctHotelsByCustomerSurname(@PathVariable String customerSurname) {
		List<Hotel> hotels = hotelService.getDistinctHotelsByCustomerSurname(customerSurname);
		return ResponseEntity.ok(hotels);
	}
}
