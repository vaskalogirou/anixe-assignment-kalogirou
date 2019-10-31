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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.helper.CurrencyUtils;
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
		booking.setCurrentRateToEuro(CurrencyUtils.getRates().get(booking.getCurrency()));
		bookingRepository.save(booking);
		return ResponseEntity.status(HttpStatus.CREATED).body(booking);
	}

	@GetMapping("/bookings")
	public List<Booking> getAllBookings() {
		List<Booking> bookings = bookingRepository.findAll();
		return bookings;
	}

	@GetMapping("bookings/{id}")
	public ResponseEntity<?> getBooking(@PathVariable Long id) {
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
}
