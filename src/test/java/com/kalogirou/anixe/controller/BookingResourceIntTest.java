package com.kalogirou.anixe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.helper.Currency;
import com.kalogirou.anixe.helper.CurrencyUtils;
import com.kalogirou.anixe.repository.BookingRepository;
import com.kalogirou.anixe.repository.HotelRepository;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class BookingResourceIntTest {
	private MockMvc mockMvc;

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

		final BookingResource bookingResource = new BookingResource(bookingRepository);
		mockMvc = MockMvcBuilders.standaloneSetup(bookingResource).build();
	}

	@Test
	@Transactional
	public void createBooking() throws Exception {
		int databaseSizeBeforeCreation = bookingRepository.findAll().size();
		mockMvc.perform(post("/api/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(booking)))
				.andExpect(status().isCreated());

		int databaseSizeAfterCreation = bookingRepository.findAll().size();
		assertThat(databaseSizeAfterCreation).isEqualTo(databaseSizeBeforeCreation + 1);
	}

	@Test
	@Transactional
	public void createBookingWithExistingId() throws Exception {
		booking.setId(1L);
		int databaseSizeBeforeCreation = bookingRepository.findAll().size();
		mockMvc.perform(post("/api/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(booking)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("A new booking cannot already have an id"));

		int databaseSizeAfterCreation = bookingRepository.findAll().size();
		assertThat(databaseSizeAfterCreation).isEqualTo(databaseSizeBeforeCreation);
	}

	@Test
	@Transactional
	public void getAllBookings() throws Exception {
		mockMvc.perform(get("/api/bookings")).andExpect(status().isOk());
	}
}
