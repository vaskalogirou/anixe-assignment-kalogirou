package com.kalogirou.anixe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kalogirou.anixe.domain.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
