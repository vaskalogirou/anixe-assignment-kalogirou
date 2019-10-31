package com.kalogirou.anixe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.kalogirou.anixe.fixture.Fixtures;
import com.kalogirou.anixe.helper.Currency;
import com.kalogirou.anixe.repository.BookingRepository;
import com.kalogirou.anixe.repository.HotelRepository;
import com.kalogirou.anixe.service.BookingService;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class BookingControllerIntTest {
	private MockMvc mockMvc;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private HotelRepository hotelRepository;

	private Booking booking;

	@BeforeEach
	public void setup() {
		Hotel hotel = Fixtures.dummyHotel();
		hotelRepository.save(hotel);

		booking = Fixtures.dummyBooking();
		booking.setHotel(hotel);

		final BookingController bookingResource = new BookingController(bookingRepository, bookingService);
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
	public void createBookingWithExistingIdReturnsBadRequest() throws Exception {
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
	public void getBookingById() throws Exception {
		bookingRepository.saveAndFlush(booking);
		mockMvc.perform(get("/api/bookings/{id}", booking.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(booking.getId().intValue()))
				.andExpect(jsonPath("$.customerName").value(Fixtures.DUMMY_CUSTOMER_NAME))
				.andExpect(jsonPath("$.customerSurname").value(Fixtures.DUMMY_CUSTOMER_SURNAME))
				.andExpect(jsonPath("$.currency").value(Fixtures.DUMMY_CURRENCY.toString()))
				.andExpect(jsonPath("$.hotel.name").value(Fixtures.DUMMY_HOTEL_NAME));
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

	@Test
	@Transactional
	public void updateBooking() throws Exception {
		bookingRepository.saveAndFlush(booking);
		String updatedCustomerName = "Michael";
		String updatedCustomerSurname = "Jackson";
		Integer updatedNumberOfPax = 4;
		Float updatedPriceAmount = 200f;
		Currency updatedCurrency = Currency.USD;

		Hotel updatedHotel = new Hotel();
		updatedHotel.setName("another hotel");
		updatedHotel.setAddress("down town");
		hotelRepository.save(updatedHotel);

		booking.setCustomerName(updatedCustomerName);
		booking.setCustomerSurname(updatedCustomerSurname);
		booking.setNumberOfPax(updatedNumberOfPax);
		booking.setPriceAmount(updatedPriceAmount);
		booking.setCurrency(updatedCurrency);
		booking.setHotel(updatedHotel);

		int databaseSizeBeforeUpdating = bookingRepository.findAll().size();

		mockMvc.perform(put("/api/bookings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(booking)))
				.andExpect(status().isOk());

		int databaseSizeAfterUpdating = bookingRepository.findAll().size();
		assertThat(databaseSizeAfterUpdating).isEqualTo(databaseSizeBeforeUpdating);

		Booking updatedBooking = bookingRepository.findById(booking.getId()).get();
		assertThat(updatedBooking.getCustomerName()).isEqualTo(updatedCustomerName);
		assertThat(updatedBooking.getCustomerSurname()).isEqualTo(updatedCustomerSurname);
		assertThat(updatedBooking.getNumberOfPax()).isEqualTo(updatedNumberOfPax);
		assertThat(updatedBooking.getPriceAmount()).isEqualTo(updatedPriceAmount);
		assertThat(updatedBooking.getCurrency()).isEqualTo(updatedCurrency);
		assertThat(updatedBooking.getHotel().getId()).isEqualTo(updatedHotel.getId());
	}
}
