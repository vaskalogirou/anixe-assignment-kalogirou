package com.kalogirou.anixe.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.fixture.Fixtures;
import com.kalogirou.anixe.helper.Currency;
import com.kalogirou.anixe.repository.BookingRepository;
import com.kalogirou.anixe.repository.HotelRepository;
import com.kalogirou.anixe.service.HotelService;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class HotelServiceImplTest {

	@Autowired
	private HotelService hotelService;

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private BookingRepository bookingRepository;
	private Hotel hotel;

	@BeforeEach
	public void setup() {
		hotel = Fixtures.dummyHotel();
		hotelRepository.saveAndFlush(hotel);
	}

	@Test
	@Transactional
	public void calculateSumOfPriceAmountsInEuroByHotelIdWhenHotelHasNoBookings() {
		float sum = hotelService.calculateSumOfPriceAmountsInEuroByHotelId(hotel.getId());
		assertThat(sum).isEqualTo(0);
	}

	@Test
	@Transactional
	public void calculateSumOfPriceAmountsInEuroByHotelId() {
		Booking firstBooking = Fixtures.dummyBooking();
		firstBooking.setCurrency(Currency.EUR);
		firstBooking.setPriceAmount(100f);
		firstBooking.setHotel(hotel);
		bookingRepository.save(firstBooking);

		Booking secondBooking = Fixtures.dummyBooking();
		secondBooking.setCurrency(Currency.USD);
		secondBooking.setPriceAmount(100f);
		secondBooking.setExchangeRateToEuro(2f);
		secondBooking.setHotel(hotel);
		bookingRepository.save(secondBooking);

		float sum = hotelService.calculateSumOfPriceAmountsInEuroByHotelId(hotel.getId());
		assertThat(sum).isEqualTo(150);
	}
}
