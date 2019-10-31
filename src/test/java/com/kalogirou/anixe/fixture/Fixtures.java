package com.kalogirou.anixe.fixture;

import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.helper.Currency;

public class Fixtures {
	public static final String DUMMY_HOTEL_NAME = "dummy hotel name";
	public static final String DUMMY_HOTEL_ADDRESS = "dummy address for a dummy hotel";
	public static final Float DUMMY_STAR_RATING = 7.5f;

	public static final String DUMMY_CUSTOMER_NAME = "john";
	public static final String DUMMY_CUSTOMER_SURNAME = "doe";
	public static final Currency DUMMY_CURRENCY = Currency.EUR;

	public static Hotel dummyHotel() {
		Hotel hotel = new Hotel();
		hotel.setName(DUMMY_HOTEL_NAME);
		hotel.setAddress(DUMMY_HOTEL_ADDRESS);
		hotel.setStarRating(DUMMY_STAR_RATING);

		return hotel;
	}

	public static Booking dummyBooking() {
		Booking booking = new Booking();
		booking.setCustomerName(DUMMY_CUSTOMER_NAME);
		booking.setCustomerSurname(DUMMY_CUSTOMER_SURNAME);
		booking.setCurrency(DUMMY_CURRENCY);
		booking.setNumberOfPax(2);
		booking.setPriceAmount(100f);

		return booking;
	}
}
