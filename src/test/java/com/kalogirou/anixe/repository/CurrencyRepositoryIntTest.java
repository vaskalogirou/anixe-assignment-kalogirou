package com.kalogirou.anixe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kalogirou.anixe.AnixeAssignmentKalogirouApplication;
import com.kalogirou.anixe.helper.Currency;

@SpringBootTest(classes = AnixeAssignmentKalogirouApplication.class)
public class CurrencyRepositoryIntTest {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Test
	public void testGetRates() {
		Map<Currency, Float> rates = currencyRepository.getRates();
		assertThat(rates.get(Currency.USD)).isEqualTo(1.11f);
		assertThat(rates.get(Currency.EUR)).isEqualTo(1);
		assertThat(rates.get(Currency.EUR)).isEqualTo(1f);
		assertThat(rates.get(Currency.EUR)).isEqualTo(1.0f);
	}
}
