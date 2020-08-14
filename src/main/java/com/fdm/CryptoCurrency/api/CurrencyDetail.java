package com.fdm.CryptoCurrency.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDetail extends DataView {
	private String id;
	private HashMap<String, String> current_price;
	private String market_cap;
	private String symbol;
	private String name;
	

	@Override
	public DataView processData(JSONObject obj) {
		CurrencyDetail cd = new CurrencyDetail();
		String id = (String) obj.get("id");
		String symbol = (String) obj.get("symbol");
		String name = (String) obj.get("name");
		JSONObject data = (JSONObject) obj.get("market_data");
		JSONObject market_caps = (JSONObject) data.get("market_cap");
		String market_cap = Long.toString(market_caps.getLong("usd"));
		JSONObject rates = (JSONObject) data.get("current_price");
		cd.setId(id);
		cd.setSymbol(symbol);
		cd.setName(name);
		cd.setMarket_cap(market_cap);
		HashMap<String, String> current_price = new HashMap<String,String>();
		for (String currency : rates.keySet()) {
			current_price.put(currency, Long.toString(rates.getLong(currency)));
		}
		cd.setCurrent_price(current_price);
		return cd;
	}

}
