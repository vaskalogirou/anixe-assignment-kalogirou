package com.kalogirou.anixe.service.impl;

import org.springframework.stereotype.Service;

import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.helper.Currency;
import com.kalogirou.anixe.helper.CurrencyUtils;
import com.kalogirou.anixe.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

	@Override
	public Float calculatePriceAmountInEuro(Booking booking) {
		Float priceAmount = booking.getPriceAmount();
		Currency currency = booking.getCurrency();
		Float bookingRate = booking.getExchangeRateToEuro();

		Float rate = bookingRate != null ? bookingRate : CurrencyUtils.getRates().get(currency);
		return priceAmount / rate;
	}
}
