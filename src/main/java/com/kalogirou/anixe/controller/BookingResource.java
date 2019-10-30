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

import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.repository.BookingRepository;

@RestController
@RequestMapping("/api")
public class BookingResource {
	private final BookingRepository bookingRepository;

	public BookingResource(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@PostMapping("/bookings")
	public ResponseEntity<?> createBooking(@Valid @RequestBody Booking booking) {
		if (booking.getId() != null) {
			return ResponseEntity.badRequest().body("A new booking cannot already have an id");
		}
		bookingRepository.save(booking);
		return ResponseEntity.status(HttpStatus.CREATED).body(booking);
	}

	@GetMapping("/bookings")
	public List<Booking> getAllBookings() {
		List<Booking> bookings = bookingRepository.findAll();
		return bookings;
	}
}
