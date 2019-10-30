package com.kalogirou.anixe.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/bookings")
	public List<Booking> getAllBookings() {
		List<Booking> bookings = bookingRepository.findAll();
		return bookings;
	}
}
