package com.kalogirou.anixe.fixture;

import com.kalogirou.anixe.domain.Hotel;

public class Fixtures {
	public static final String DUMMY_HOTEL_NAME = "dummy hotel name";
	public static final String DUMMY_HOTEL_ADDRESS = "dummy address for a dummy hotel";
	public static final Float DUMMY_STAR_RATING = 7.5f;

	public static Hotel dummyHotel() {
		Hotel hotel = new Hotel();
		hotel.setName(DUMMY_HOTEL_NAME);
		hotel.setAddress(DUMMY_HOTEL_ADDRESS);
		hotel.setStarRating(DUMMY_STAR_RATING);
		return hotel;
	}
}
