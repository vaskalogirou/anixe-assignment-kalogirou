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

import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.repository.BookingRepository;
import com.kalogirou.anixe.service.BookingService;

@RestController
@RequestMapping("/api")
public class BookingController {
	private final BookingRepository bookingRepository;

	private final BookingService bookingService;

	public BookingController(BookingRepository bookingRepository, BookingService bookingService) {
		this.bookingRepository = bookingRepository;
		this.bookingService = bookingService;
	}

	@PostMapping("/bookings")
	public ResponseEntity<?> createBooking(@Valid @RequestBody Booking booking) {
		if (booking.getId() != null) {
			return ResponseEntity.badRequest().body("A new booking cannot already have an id");
		}
		bookingService.save(booking);
		return ResponseEntity.status(HttpStatus.CREATED).body(booking);
	}

	@GetMapping("/bookings")
	public List<Booking> getAllBookings() {
		List<Booking> bookings = bookingRepository.findAll();
		return bookings;
	}

	@GetMapping("/bookings/{id}")
	public ResponseEntity<?> getBookingById(@PathVariable Long id) {
		Optional<Booking> booking = bookingRepository.findById(id);
		if (booking.isPresent()) {
			return ResponseEntity.ok(booking.get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/bookings/{id}")
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/bookings")
	public ResponseEntity<?> updateBooking(@Valid @RequestBody Booking booking) {
		if (booking.getId() == null) {
			return ResponseEntity.badRequest().body("Request to update a booking should have a booking id");
		}
		bookingRepository.save(booking);
		return ResponseEntity.ok().body(booking);
	}

	@GetMapping("/bookings-by-hotel-id/{hotelId}")
	public ResponseEntity<?> getBookingsByHotelId(@PathVariable Long hotelId) {
		List<Booking> bookings = bookingRepository.findAllByHotelId(hotelId);
		return ResponseEntity.ok().body(bookings);
	}
}
