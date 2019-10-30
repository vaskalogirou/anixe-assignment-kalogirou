package com.kalogirou.anixe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.helper.Currency;
import com.kalogirou.anixe.helper.CurrencyUtils;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class BookingRepositoryIntTest {
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private HotelRepository hotelRepository;

	private Booking booking;

	@BeforeEach
	public void setup() {
		Hotel hotel = new Hotel();
		hotel.setName("dummy hotel");
		hotel.setAddress("dummy address");
		hotel.setStarRating(7.5f);
		hotelRepository.save(hotel);

		booking = new Booking();
		booking.setCustomerName("john");
		booking.setCustomerSurname("doe");
		booking.setCurrency("EUR");
		booking.setNumberOfPax(2);
		booking.setPriceAmount(100f);
		booking.setCurrentRateToEuro(CurrencyUtils.getRates().get(Currency.EUR));
		booking.setHotel(hotel);
	}

	@Test
	@Transactional
	public void saveBooking() {
		int databaseSizeBeforeSave = bookingRepository.findAll().size();
		bookingRepository.saveAndFlush(booking);
		int databaseSizeAfterSave = bookingRepository.findAll().size();
		assertThat(databaseSizeAfterSave).isEqualTo(databaseSizeBeforeSave + 1);
	}
}
