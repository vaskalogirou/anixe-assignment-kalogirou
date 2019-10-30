package com.kalogirou.anixe.helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class CurrencyUtilsTest {

	@Test
	public void testGetRates() {
		Map<Currency, Float> rates = CurrencyUtils.getRates();
		assertThat(rates.get(Currency.USD)).isEqualTo(1.11f);
		assertThat(rates.get(Currency.EUR)).isEqualTo(1);
		assertThat(rates.get(Currency.EUR)).isEqualTo(1f);
		assertThat(rates.get(Currency.EUR)).isEqualTo(1.0f);
	}

	@Test
	public void testGetAmountInEuro() {
		assertThat(CurrencyUtils.getAmountInEuro(123f, Currency.EUR)).isEqualTo(123f);

		// If you have a thousand rubles in your pocket, you only have about 14 euros
		assertThat(CurrencyUtils.getAmountInEuro(1000f, Currency.RUB)).isCloseTo(14.12f, within(0.001f));

		// Amount is rounded to a reasonable offset
		assertThat(CurrencyUtils.getAmountInEuro(1000f, Currency.RUB)).isNotCloseTo(14.12f, within(0.0001f));
	}
}
