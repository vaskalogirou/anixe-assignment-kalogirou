package com.kalogirou.anixe.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kalogirou.anixe.helper.Currency;

@Service
public class CurrencyRepository {
	public Float getRate(Currency currency) {
		return getRates().get(currency);
	}

	private Map<Currency, Float> getRates() {
		Map<Currency, Float> rates = new HashMap<Currency, Float>();
		rates.put(Currency.EUR, 1f);
		rates.put(Currency.USD, 1.11f);
		rates.put(Currency.JPY, 120.59f);
		rates.put(Currency.GBP, 0.87f);
		rates.put(Currency.RUB, 70.82f);
		return rates;
	}
}
