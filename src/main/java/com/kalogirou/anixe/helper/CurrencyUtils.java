package com.kalogirou.anixe.helper;

import java.util.HashMap;
import java.util.Map;

public class CurrencyUtils {

	// Euro is the base currency
	public static Map<Currency, Float> getRates() {
		Map<Currency, Float> rates = new HashMap<Currency, Float>();
		rates.put(Currency.EUR, 1f);
		rates.put(Currency.USD, 1.11f);
		rates.put(Currency.JPY, 120.59f);
		rates.put(Currency.GBP, 0.87f);
		rates.put(Currency.RUB, 70.82f);
		return rates;
	}

	public static float getAmountInEuro(Float amount, Currency currency) {
		return amount / getRates().get(currency);
	}
}
