package com.kalogirou.anixe.helper;

import java.util.HashMap;
import java.util.Map;

public class CurrencyUtils {

	// Euro is the base currency
	public static Map<String, Float> getRates() {
		Map<String, Float> rates = new HashMap<String, Float>();
		rates.put("EUR", 1f);
		rates.put("USD", 1.11f);
		rates.put("JPY", 120.59f);
		rates.put("GBP", 0.87f);
		rates.put("RUB", 70.82f);
		return rates;
	}

	public static float getAmountInEuro(Float amount, String currency) {
		return amount / getRates().get(currency);
	}
}
