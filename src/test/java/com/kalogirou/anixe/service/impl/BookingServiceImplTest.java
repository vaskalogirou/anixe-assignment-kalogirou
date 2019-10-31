package com.kalogirou.anixe.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.helper.Currency;
import com.kalogirou.anixe.repository.CurrencyRepository;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class BookingServiceImplTest {

	@InjectMocks
	private BookingServiceImpl bookingService;

	@Mock
	private CurrencyRepository currencyRepository;

	@BeforeEach
	public void setup() {
		when(currencyRepository.getRate(eq(Currency.USD))).thenReturn(1.5f);
		when(currencyRepository.getRate(eq(Currency.EUR))).thenReturn(1f);
	}

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
		booking.setPriceAmount(100f);

		// 100 dollars is only about 67 euros, if the rate is 1.5
		float amountInEuro = bookingService.calculatePriceAmountInEuro(booking);
		assertThat(amountInEuro).isCloseTo(66.6666f, within(0.001f));
	}

	@Test
	public void calculatePriceAmountInEuroWhenBookingIsNotInEuroAndAlreadyHasARate() {
		Booking booking = new Booking();
		booking.setCurrency(Currency.RUB);
		booking.setPriceAmount(100f);
		booking.setExchangeRateToEuro(50f);

		float amountInEuro = bookingService.calculatePriceAmountInEuro(booking);
		assertThat(amountInEuro).isCloseTo(2f, within(0.001f));
	}
}
