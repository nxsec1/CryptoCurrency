package com.fdm.CryptoCurrency.service;

import java.math.BigDecimal;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fdm.CryptoCurrency.api.CurrencyDetail;
import com.fdm.CryptoCurrency.client.CoinGeckoClient;

@Service
public class CoinService {
	
	public CurrencyDetail getCurrencyDetail(String id) {
		CurrencyDetail cd = new CurrencyDetail();
		JSONObject obj= new CoinGeckoClient().getJson("https://api.coingecko.com/api/v3/coins/" + id);
		String id_name = (String) obj.get("id");
		cd.setId(id_name);

		String symbol = (String) obj.get("symbol");
		cd.setSymbol(symbol);

		String name = (String) obj.get("name");
		cd.setName(name);

		JSONObject data = (JSONObject) obj.get("market_data");
		JSONObject market_caps = (JSONObject) data.get("market_cap");
		String market_cap = Long.toString(market_caps.getLong("usd"));
		cd.setMarket_cap(market_cap);

		JSONObject rates = (JSONObject) data.get("current_price");		
		HashMap<String, String> current_price = new HashMap<String,String>();
		for (String currency : rates.keySet()) {
			Double price =  rates.getDouble(currency);
			current_price.put(currency,String.format("%.2f", price));
		}
		cd.setCurrent_price(current_price);
		
		JSONObject changes = (JSONObject) data.get("price_change_percentage_24h_in_currency");
		HashMap<String, String> price_change = new HashMap<String,String>();
		for (String currency : changes.keySet()) {
			price_change.put(currency, Double.toString(changes.getDouble(currency)));
		}
		cd.setPrice_percentage_change_in_24hr(price_change);
		return cd;
	}
	
	
}
