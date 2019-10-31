package com.kalogirou.anixe.service.impl;

import org.springframework.stereotype.Service;

import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.helper.Currency;
import com.kalogirou.anixe.repository.BookingRepository;
import com.kalogirou.anixe.repository.CurrencyRepository;
import com.kalogirou.anixe.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	private CurrencyRepository currencyRepository;

	private BookingRepository bookingRepository;

	public BookingServiceImpl(CurrencyRepository currencyRepository, BookingRepository bookingRepository) {
		this.currencyRepository = currencyRepository;
		this.bookingRepository = bookingRepository;
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
		bookingRepository.save(booking);
		return booking;
	}
}
