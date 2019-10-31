package com.kalogirou.anixe.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.repository.BookingRepository;
import com.kalogirou.anixe.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	private BookingRepository bookingRepository;

	public HotelServiceImpl(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Override
	public Float calculateSumOfPriceAmountsInEuroByHotelId(Long id) {
		List<Booking> bookings = bookingRepository.findAllByHotelId(id);
		Float sum = 0f;
		for (Booking booking : bookings) {
			float rate = 1;
			if (booking.getCurrentRateToEuro() != null) {
			}
		}
		return null;
	}
}
