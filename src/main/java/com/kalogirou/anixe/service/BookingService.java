package com.kalogirou.anixe.service;

import com.kalogirou.anixe.domain.Booking;

public interface BookingService {
	float calculatePriceAmountInEuro(Booking booking);

	Booking save(Booking booking);
}
