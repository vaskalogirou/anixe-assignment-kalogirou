package com.kalogirou.anixe.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Booking implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String customerName;
	private String customerSurname;
	private Integer numberOfPax;

	@ManyToOne
	private Hotel hotel;

	private Float priceAmount;
	private String currency;
	private Float priceAmountInEuro;

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Float getPriceAmountInEuro() {
		return priceAmountInEuro;
	}

	public void setPriceAmountInEuro(Float priceAmountInEuro) {
		this.priceAmountInEuro = priceAmountInEuro;
	}
}
