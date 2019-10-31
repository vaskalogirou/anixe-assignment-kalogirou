package com.kalogirou.anixe.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.domain.Hotel;
import com.kalogirou.anixe.fixture.Fixtures;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class HotelRepositoryIntTest {
	@Autowired
	private HotelRepository hotelRepository;

	private Hotel hotel;

	@BeforeEach
	public void setup() {
		hotel = Fixtures.dummyHotel();
	}

	@Test
	@Transactional
	public void saveHotelWithoutName() {
		hotel.setName(null);
		assertThrows(ConstraintViolationException.class, () -> hotelRepository.saveAndFlush(hotel));
	}

	@Test
	@Transactional
	public void saveHotelWithoutAddress() {
		hotel.setAddress(null);
		assertThrows(ConstraintViolationException.class, () -> hotelRepository.saveAndFlush(hotel));
	}
}
