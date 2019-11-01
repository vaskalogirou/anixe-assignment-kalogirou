package com.kalogirou.anixe.service.impl;

import org.springframework.stereotype.Service;

import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.helper.Currency;
import com.kalogirou.anixe.repository.BookingRepository;
import com.kalogirou.anixe.repository.CurrencyRepository;
import com.kalogirou.anixe.repository.HotelRepository;
import com.kalogirou.anixe.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	private BookingRepository bookingRepository;

	private HotelRepository hotelRepository;

	private CurrencyRepository currencyRepository;

	public BookingServiceImpl(BookingRepository bookingRepository, HotelRepository hotelRepository, CurrencyRepository currencyRepository) {
		this.bookingRepository = bookingRepository;
		this.hotelRepository = hotelRepository;
		this.currencyRepository = currencyRepository;
	}

	@Override
	public float calculatePriceAmountInEuro(Booking booking) {
		Float priceAmount = booking.getPriceAmount();
		Currency currency = booking.getCurrency();
		Float bookingRate = booking.getExchangeRateToEuro();

		Float rate = bookingRate != null ? bookingRate : currencyRepository.getRate(currency);
		return priceAmount / rate;
	}

	@Override
	public Booking save(Booking booking) {
		booking.setExchangeRateToEuro(currencyRepository.getRate(booking.getCurrency()));
		if (booking.getHotel().getId() == null) {
			hotelRepository.save(booking.getHotel());
		}
		bookingRepository.save(booking);
		return booking;
	}
}
