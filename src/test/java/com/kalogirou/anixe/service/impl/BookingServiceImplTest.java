package com.kalogirou.anixe.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.helper.Currency;
import com.kalogirou.anixe.service.BookingService;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class BookingServiceImplTest {

	@Autowired
	private BookingService bookingService;

	@Test
	public void calculatePriceAmountInEuroWhenBookingIsAlreadyInEuro() {
		Booking booking = new Booking();
		booking.setCurrency(Currency.EUR);
		booking.setPriceAmount(123f);
		Float amountInEuro = bookingService.calculatePriceAmountInEuro(booking);
		assertThat(amountInEuro).isCloseTo(booking.getPriceAmount(), within(0.001f));
	}

	@Test
	public void calculatePriceAmountInEuroWhenBookingIsNotInEuro() {
		Booking booking = new Booking();
		booking.setCurrency(Currency.USD);
	}
}
