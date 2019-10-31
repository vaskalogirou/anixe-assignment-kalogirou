package com.kalogirou.anixe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.fixture.Fixtures;
import com.kalogirou.anixe.repository.HotelRepository;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class HotelControllerIntTest {

	@Autowired
	private HotelRepository hotelRepository;

	private MockMvc mockMvc;

	private Hotel hotel;

	@BeforeEach
	public void setup() {
		hotel = Fixtures.dummyHotel();

		final HotelController hotelResource = new HotelController(hotelRepository);
		mockMvc = MockMvcBuilders.standaloneSetup(hotelResource).build();
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
		mockMvc.perform(post("/api/hotels")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(hotel)))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("A new hotel cannot already have an id"));

		int databaseSizeAfterCreation = hotelRepository.findAll().size();
		assertThat(databaseSizeAfterCreation).isEqualTo(databaseSizeBeforeCreation);
	}

	@Test
	@Transactional
	public void createHotelCheckNameIsNotBeBlank() throws Exception {
		int databaseSizeBeforeCreation = hotelRepository.findAll().size();
		hotel.setName("   ");
		mockMvc.perform(post("/api/hotels")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(hotel)))
				.andExpect(status().isBadRequest());

		int databaseSizeAfterCreation = hotelRepository.findAll().size();
		assertThat(databaseSizeAfterCreation).isEqualTo(databaseSizeBeforeCreation);
	}

	@Test
	@Transactional
	public void createHotelAddressShouldBeAtLeastThreeChars() throws Exception {
		int databaseSizeBeforeCreation = hotelRepository.findAll().size();
		hotel.setAddress("GR");
		mockMvc.perform(post("/api/hotels")
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
		mockMvc.perform(get("/api/hotels"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[*].id").value(hasItem(hotel.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(Fixtures.DUMMY_HOTEL_NAME)))
				.andExpect(jsonPath("$.[*].address").value(hasItem(Fixtures.DUMMY_HOTEL_ADDRESS)));
	}

	@Test
	@Transactional
	public void getHotel() throws Exception {
		hotelRepository.saveAndFlush(hotel);
		mockMvc.perform(get("/api/hotels/{id}", hotel.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(hotel.getId().intValue()))
				.andExpect(jsonPath("$.name").value(Fixtures.DUMMY_HOTEL_NAME))
				.andExpect(jsonPath("$.address").value(Fixtures.DUMMY_HOTEL_ADDRESS))
				.andExpect(jsonPath("$.starRating").value(Fixtures.DUMMY_STAR_RATING));
	}

	@Test
	@Transactional
	public void getNonExistingHotel() throws Exception {
		mockMvc.perform(get("/api/hotels/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void deleteHotel() throws Exception {
		hotelRepository.saveAndFlush(hotel);
		int databaseSizeBeforeDeletion = hotelRepository.findAll().size();
		mockMvc.perform(delete("/api/hotels/{id}", hotel.getId())).andExpect(status().isNoContent());

		int databaseSizeAfterDeletion = hotelRepository.findAll().size();
		assertThat(databaseSizeAfterDeletion).isEqualTo(databaseSizeBeforeDeletion - 1);
	}

	@Test
	@Transactional
	public void updateHotel() throws Exception {
		hotelRepository.saveAndFlush(hotel);
		String updatedName = "another dummy name";
		String updatedAddress = "Omonoia square";
		Float updatedRating = 9.3f;

		int databaseSizeBeforeUpdating = hotelRepository.findAll().size();
		hotel.setName(updatedName);
		hotel.setAddress(updatedAddress);
		hotel.setStarRating(updatedRating);

		mockMvc.perform(put("/api/hotels")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(hotel)))
				.andExpect(status().isOk());

		int databaseSizeAfterUpdating = hotelRepository.findAll().size();
		assertThat(databaseSizeAfterUpdating).isEqualTo(databaseSizeBeforeUpdating);

		Hotel updatedHotel = hotelRepository.findById(hotel.getId()).get();
		assertThat(updatedHotel.getName()).isEqualTo(updatedName);
		assertThat(updatedHotel.getAddress()).isEqualTo(updatedAddress);
		assertThat(updatedHotel.getStarRating()).isEqualTo(updatedRating);
	}
}
