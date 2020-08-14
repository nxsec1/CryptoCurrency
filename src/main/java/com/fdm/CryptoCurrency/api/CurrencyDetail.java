package com.fdm.CryptoCurrency.api;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDetail extends DataView {
	private String id;
	private String symbol;
	private String name;
	private String market_cap;
	private String genesis_date;
	private String last_update;
	private HashMap<String, String> current_price;
	private HashMap<String, String> price_percentage_change_in_24hr;
	private HashMap<String, String> lastWeek_price;


	@Override
	public DataView processData(JSONObject obj) {
		CurrencyDetail cd = new CurrencyDetail();
		
		String id = (String) obj.get("id");
		cd.setId(id);

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
			current_price.put(currency, Long.toString(rates.getLong(currency)));
		}
		cd.setCurrent_price(current_price);
		
		return cd;
	}

}
