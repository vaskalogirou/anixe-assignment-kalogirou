package com.kalogirou.anixe.service;

import java.util.List;

import com.kalogirou.anixe.domain.Hotel;

public interface HotelService {
	float calculateSumOfPriceAmountsInEuroByHotelId(Long hotelId);

	List<Hotel> getDistinctHotelsByCustomerSurname(String customerSurname);
}
