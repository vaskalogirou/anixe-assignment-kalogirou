package com.kalogirou.anixe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.repository.HotelRepository;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class HotelResourceIT {
	private static final String DUMMY_NAME = "dummy hotel name";
	private static final String DUMMY_ADDRESS = "Syntagma Square";
	private static final float DUMMY_RATING = 8.3f;

	@Autowired
	private HotelRepository hotelRepository;

	private MockMvc mockMvc;

	private Hotel hotel;

	@BeforeEach
	public void setup() {
		hotel = new Hotel();
		hotel.setName(DUMMY_NAME);
		hotel.setAddress(DUMMY_ADDRESS);
		hotel.setStarRating(DUMMY_RATING);

		final HotelResource hotelResource = new HotelResource(hotelRepository);
		this.mockMvc = MockMvcBuilders.standaloneSetup(hotelResource).build();
	}

	@Test
	@Transactional
	public void createHotel() throws Exception {
		int databaseSizeBeforeCreation = hotelRepository.findAll().size();
		this.mockMvc.perform(post("/api/hotels")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(hotel)))
				.andExpect(status().isCreated());

		int databaseSizeAfterCreation = hotelRepository.findAll().size();
		assertThat(databaseSizeAfterCreation).isEqualTo(databaseSizeBeforeCreation + 1);
	}

	@Test
	@Transactional
	public void createHotelWithExistingId() throws Exception {
		int databaseSizeBeforeCreation = hotelRepository.findAll().size();
		hotel.setId(1L);
		this.mockMvc.perform(post("/api/hotels")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(hotel)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("A new hotel cannot already have an id"));

		int databaseSizeAfterCreation = hotelRepository.findAll().size();
		assertThat(databaseSizeAfterCreation).isEqualTo(databaseSizeBeforeCreation);
	}

	@Test
	@Transactional
	public void checkNameIsShouldNotBeBlank() throws Exception {
		int databaseSizeBeforeCreation = hotelRepository.findAll().size();
		hotel.setName("   ");
		this.mockMvc.perform(post("/api/hotels")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(hotel)))
				.andExpect(status().isBadRequest());

		int databaseSizeAfterCreation = hotelRepository.findAll().size();
		assertThat(databaseSizeAfterCreation).isEqualTo(databaseSizeBeforeCreation);
	}

	@Test
	@Transactional
	public void checkAddressShouldBeAtLeastThreeChars() throws Exception {
		int databaseSizeBeforeCreation = hotelRepository.findAll().size();
		hotel.setAddress("GR");
		this.mockMvc.perform(post("/api/hotels")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(hotel)))
				.andExpect(status().isBadRequest());

		int databaseSizeAfterCreation = hotelRepository.findAll().size();
		assertThat(databaseSizeAfterCreation).isEqualTo(databaseSizeBeforeCreation);
	}

	@Test
	@Transactional
	public void getAllHotels() throws Exception {
		hotelRepository.saveAndFlush(hotel);
		this.mockMvc.perform(get("/api/hotels"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[*].id").value(hasItem(hotel.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DUMMY_NAME)))
				.andExpect(jsonPath("$.[*].address").value(hasItem(DUMMY_ADDRESS)));
	}
}
