package com.kalogirou.anixe.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
		booking.setCurrency(Currency.EUR);
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

	@Test
	@Transactional
	public void saveBookingWithoutCustomerName() {
		booking.setCustomerName(null);
		assertThrows(ConstraintViolationException.class, () -> bookingRepository.saveAndFlush(booking));
	}

	@Test
	@Transactional
	public void saveBookingWithoutHotel() {
		booking.setHotel(null);
		assertThrows(ConstraintViolationException.class, () -> bookingRepository.saveAndFlush(booking));
	}

	@Test
	@Transactional
	public void saveBookingWithInexistingHotel() {
		Hotel unregisteredHotel = new Hotel();
		unregisteredHotel.setId(123456L);
		unregisteredHotel.setName("test hotel");
		unregisteredHotel.setAddress("test address");
		booking.setHotel(unregisteredHotel);
		assertThrows(DataIntegrityViolationException.class, () -> bookingRepository.saveAndFlush(booking));
	}

	@Test
	@Transactional
	public void saveBookingWithBadlyFormedHotel() {
		booking.getHotel().setName(null);
		assertThrows(ConstraintViolationException.class, () -> bookingRepository.saveAndFlush(booking));
	}
}
