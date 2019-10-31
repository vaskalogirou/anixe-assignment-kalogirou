package com.kalogirou.anixe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalogirou.anixe.domain.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findAllByHotelId(Long id);

	List<Booking> findByCustomerSurname(String customerSurname);
}
