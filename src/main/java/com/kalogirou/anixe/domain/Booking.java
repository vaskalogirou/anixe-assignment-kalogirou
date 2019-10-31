package com.kalogirou.anixe.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kalogirou.anixe.helper.Currency;

@Entity
public class Booking implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String customerName;

	@NotBlank
	private String customerSurname;

	@NotNull
	private Integer numberOfPax;

	@NotNull
	@ManyToOne
	private Hotel hotel;

	@NotNull
	private Float priceAmount;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Currency currency;

	private Float currentRateToEuro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerSurname() {
		return customerSurname;
	}

	public void setCustomerSurname(String customerSurname) {
		this.customerSurname = customerSurname;
	}

	public Integer getNumberOfPax() {
		return numberOfPax;
	}

	public void setNumberOfPax(Integer numberOfPax) {
		this.numberOfPax = numberOfPax;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Float getPriceAmount() {
		return priceAmount;
	}

	public void setPriceAmount(Float priceAmount) {
		this.priceAmount = priceAmount;
	}

	public Float getCurrentRateToEuro() {
		return currentRateToEuro;
	}

	public void setCurrentRateToEuro(Float currentRateToEuro) {
		this.currentRateToEuro = currentRateToEuro;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
