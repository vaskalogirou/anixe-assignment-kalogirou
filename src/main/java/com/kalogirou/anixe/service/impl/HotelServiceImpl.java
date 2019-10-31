package com.kalogirou.anixe.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.repository.BookingRepository;
import com.kalogirou.anixe.service.BookingService;
import com.kalogirou.anixe.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	private BookingRepository bookingRepository;

	private BookingService bookingService;

	public HotelServiceImpl(BookingRepository bookingRepository, BookingService bookingService) {
		this.bookingRepository = bookingRepository;
		this.bookingService = bookingService;
	}

	@Override
	public float calculateSumOfPriceAmountsInEuroByHotelId(Long id) {
		List<Booking> bookings = bookingRepository.findAllByHotelId(id);
		float sum = 0f;
		for (Booking booking : bookings) {
			sum += bookingService.calculatePriceAmountInEuro(booking);
		}
		return sum;
	}

	@Override
	public List<Hotel> getDistinctHotelsByCustomerSurname(String customerSurname) {
		List<Booking> bookings = bookingRepository.findByCustomerSurname(customerSurname);
		List<Hotel> hotels = bookings.stream().map(Booking::getHotel).distinct().collect(Collectors.toList());
		return hotels;
	}
}
