package com.fdm.CryptoCurrency.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fdm.CryptoCurrency.api.CurrencyDetail;
import com.fdm.CryptoCurrency.client.CoinGeckoClient;

@Service
public class CoinService {

	public CurrencyDetail getCurrencyDetail(String id) {
		CurrencyDetail cd = new CurrencyDetail();
		JSONObject obj = new CoinGeckoClient().getJson("https://api.coingecko.com/api/v3/coins/" + id);
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

		String genesis_date = (String) obj.get("genesis_date");
		cd.setGenesis_date(formatDate(genesis_date));

		String last_update = (String) obj.get("last_updated");
		cd.setLast_update(formatDate(last_update.substring(0, 10)));

		JSONObject rates = (JSONObject) data.get("current_price");
		HashMap<String, String> current_price = new HashMap<String, String>();
		for (String currency : rates.keySet()) {
			Double price = rates.getDouble(currency);
			current_price.put(currency, String.format("%.2f", price));
		}
		cd.setCurrent_price(current_price);

		JSONObject changes = (JSONObject) data.get("price_change_percentage_24h_in_currency");
		HashMap<String, String> price_change = getPrice(changes);
		//		for (String currency : changes.keySet()) {
//			price_change.put(currency, Double.toString(changes.getDouble(currency)));
//		}
		cd.setPrice_percentage_change_in_24hr(price_change);

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yy");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime then = now.minusDays(7);
		String date = String.format(then.format(format));
		// https://api.coingecko.com/api/v3/coins/bitcoin/history?date=10-08-2020

		JSONObject historyObj = new CoinGeckoClient()
				.getJson("https://api.coingecko.com/api/v3/coins/" + id + "/history?date=" + date);
		
		return cd;
	}

	public String formatDate(String dateString) {
		LocalDate date = LocalDate.parse(dateString);
		String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yy"));
		return formattedDate;
	}
	
	public HashMap<String, String> getPrice(JSONObject obj){
		HashMap<String, String> price = new HashMap<String, String>();
		for (String currency : obj.keySet()) {
			price.put(currency, Double.toString(obj.getDouble(currency)));
		}
		return price;
	}

}
