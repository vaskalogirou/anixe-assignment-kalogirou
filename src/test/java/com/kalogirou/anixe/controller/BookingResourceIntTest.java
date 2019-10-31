package com.kalogirou.anixe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.domain.Booking;
import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.helper.Currency;
import com.kalogirou.anixe.repository.BookingRepository;
import com.kalogirou.anixe.repository.HotelRepository;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class BookingResourceIntTest {
	private static final String CUSTOMER_NAME = "john";
	private static final String CUSTOMER_SURNAME = "doe";
	private static final String HOTEL_NAME = "dummy hotel name";
	private MockMvc mockMvc;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private HotelRepository hotelRepository;

	private Booking booking;

	@BeforeEach
	public void setup() {
		Hotel hotel = new Hotel();
		hotel.setName(HOTEL_NAME);
		hotel.setAddress("dummy address");
		hotel.setStarRating(7.5f);
		hotelRepository.save(hotel);

		booking = new Booking();
		booking.setCustomerName(CUSTOMER_NAME);
		booking.setCustomerSurname(CUSTOMER_SURNAME);
		booking.setCurrency(Currency.EUR);
		booking.setNumberOfPax(2);
		booking.setPriceAmount(100f);
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
	public void createBookingCheckCustomerNameIsNotBlank() throws Exception {
		booking.setCustomerName("	 ");
		MvcResult mvcResult = mockMvc.perform(post("/api/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(booking)))
				.andExpect(status().isBadRequest())
				.andReturn();

		String response = mvcResult.getResolvedException().getMessage();
		assertThat(response).contains("Field error in object 'booking' on field 'customerName': rejected value");
	}

	@Test
	@Transactional
	public void getAllBookings() throws Exception {
		mockMvc.perform(get("/api/bookings")).andExpect(status().isOk());
	}

	@Test
	@Transactional
	public void getBooking() throws Exception {
		bookingRepository.saveAndFlush(booking);
		mockMvc.perform(get("/api/bookings/{id}", booking.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(booking.getId().intValue()))
				.andExpect(jsonPath("$.customerName").value(CUSTOMER_NAME))
				.andExpect(jsonPath("$.customerSurname").value(CUSTOMER_SURNAME))
				.andExpect(jsonPath("$.currency").value("EUR"))
				.andExpect(jsonPath("$.hotel.name").value(HOTEL_NAME));
	}

	@Test
	@Transactional
	public void getNonExistingBooking() throws Exception {
		mockMvc.perform(get("/api/bookings/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void deleteBooking() throws Exception {
		bookingRepository.saveAndFlush(booking);
		int databaseSizeBeforeDeletion = bookingRepository.findAll().size();
		mockMvc.perform(delete("/api/bookings/{id}", booking.getId())).andExpect(status().isNoContent());

		int databaseSizeAfterDeletion = bookingRepository.findAll().size();
		assertThat(databaseSizeAfterDeletion).isEqualTo(databaseSizeBeforeDeletion - 1);
	}
}
