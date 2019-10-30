package com.kalogirou.anixe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.repository.BookingRepository;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class BookingResourceIntTest {
	private MockMvc mockMvc;

	@Autowired
	private BookingRepository bookingRepository;

	@BeforeEach
	public void setup() {
		final BookingResource bookingResource = new BookingResource(bookingRepository);
		mockMvc = MockMvcBuilders.standaloneSetup(bookingResource).build();
	}

	@Test
	@Transactional
	public void getAllBookings() throws Exception {
		mockMvc.perform(get("/api/bookings")).andExpect(status().isOk());
	}
}
