package com.fdm.CryptoCurrency.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fdm.CryptoCurrency.api.CryptoCurrency;
import com.fdm.CryptoCurrency.api.CurrencyDetail;
import com.fdm.CryptoCurrency.api.StatusUpdate;
import com.fdm.CryptoCurrency.client.CoinGeckoClient;

@Service
public class CoinService {

	public CurrencyDetail getCurrencyDetail(String id) {
		CurrencyDetail cd = new CurrencyDetail();
//		JSONObject obj = new CoinGeckoClient().getJson("https://api.coingecko.com/api/v3/coins/" + id);
		JSONObject obj = new CoinGeckoClient().getJson("http://localhost:8080/api/coins/" + id);
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
		HashMap<String, String> current_price = getPrice(rates);
		cd.setCurrent_price(current_price);

		JSONObject changes = (JSONObject) data.get("price_change_percentage_24h_in_currency");
		HashMap<String, String> price_change = getPriceChange(changes);
		cd.setPrice_percentage_change_in_24hr(price_change);

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime then = now.minusDays(7);
		String date = String.format(then.format(format));

		// https://api.coingecko.com/api/v3/coins/bitcoin/history?date=10-08-2020
//		JSONObject historyObj = new CoinGeckoClient()
//				.getJson("https://api.coingecko.com/api/v3/coins/" + id + "/history?date=" + date);
		JSONObject historyObj = new CoinGeckoClient().getJson("http://localhost:8080/api/coins/bitcoin/history");
		JSONObject historyData = (JSONObject) historyObj.get("market_data");
		JSONObject hisotryRates = (JSONObject) historyData.get("current_price");
		HashMap<String, String> history_price = getPrice(hisotryRates);
		cd.setLastWeek_price(history_price);
		return cd;
	}

	public String formatDate(String dateString) {
		LocalDate date = LocalDate.parse(dateString);
		String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		return formattedDate;
	}

	public HashMap<String, String> getPriceChange(JSONObject obj) {
		List<String> currencies = new ArrayList<>(Arrays.asList("jpy", "aud", "usd"));
		HashMap<String, String> price = new HashMap<String, String>();
		for (String currency : obj.keySet()) {
			if (currencies.contains(currency)) {
				price.put(currency, Double.toString(obj.getDouble(currency)));
			}
		}
		return price;
	}

	public HashMap<String, String> getPrice(JSONObject obj) {
		List<String> currencies = new ArrayList<>(Arrays.asList("jpy", "aud", "usd"));
		HashMap<String, String> price = new HashMap<String, String>();
		for (String currency : obj.keySet()) {
			if (currencies.contains(currency)) {
				Double p = obj.getDouble(currency);
				price.put(currency, String.format("%.2f", p));
			}
		}
		return price;
	}

	public ArrayList<CryptoCurrency> getAll(String currency, String page) {
		// https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd
		ArrayList<CryptoCurrency> cryptoCurrencys = new ArrayList<CryptoCurrency>();
		// https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10&page=1&sparkline=false
//		JSONArray array = new CoinGeckoClient()
//				.getJsonArray("https://api.coingecko.com/api/v3/coins/markets?vs_currency=" + currency
//						+ "&order=market_cap_desc&per_page=10&page=" + page);
		JSONArray array = new CoinGeckoClient().getJsonArray("http://localhost:8080/api/coins/market?currency="+currency+"&page="+page);
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			CryptoCurrency cryptoCurrency = new CryptoCurrency();
			String id_name = (String) obj.get("id");
			cryptoCurrency.setId(id_name);

			Double current_price = obj.getDouble("current_price");
			cryptoCurrency.setCurrent_price(String.format("%.2f", current_price));

			String market_cap = Long.toString(obj.getLong("market_cap"));
			cryptoCurrency.setMarket_cap(market_cap);

			// https://api.coingecko.com/api/v3/coins/bitcoin/status_updates

//			JSONObject obj2 = new CoinGeckoClient()
//					.getJson("https://api.coingecko.com/api/v3/coins/" + id_name + "/status_updates");
			JSONObject obj2 = new CoinGeckoClient().getJson("http://localhost:8080/coins/"+id_name+"/status_updates");
			JSONArray data = (JSONArray) obj2.get("status_updates");
			if (data != null) {
				ArrayList<StatusUpdate> updates = new ArrayList<StatusUpdate>();
				for (int j = 0; j < data.length(); j++) {
					StatusUpdate update = new StatusUpdate();
					JSONObject updateObj = data.getJSONObject(j);
					String title = (String) updateObj.get("user_title");
					update.setTitle(title);
					String description = (String) updateObj.get("description");
					update.setDescription(description);
					String created_at = (String) updateObj.get("created_at");
					update.setCreated_at(formatDate(created_at.substring(0, 10)));
					updates.add(update);
				}
				cryptoCurrency.setStatusUpdates(updates);
			}
			cryptoCurrencys.add(cryptoCurrency);
		}
		return cryptoCurrencys;
	}

}
