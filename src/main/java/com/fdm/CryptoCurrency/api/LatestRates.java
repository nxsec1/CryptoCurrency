package com.fdm.CryptoCurrency.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class LatestRates extends DataView {
	private String id;
	private Map<String,Double> market_cap;
	

	public Map<String, Double> getMarket_cap() {
		return market_cap;
	}

	public void setMarket_cap(Map<String, Double> market_cap) {
		this.market_cap = market_cap;
	}

	public LatestRates() {
		super();
		this.market_cap = new HashMap<>();
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public DataView processData(JSONObject obj) {
		LatestRates lr = new LatestRates();
		String id = (String) obj.get("id");
//		JSONObject rates = (JSONObject) obj.get("market_cap");
//		for (String name : rates.keySet()) {
//			lr.market_cap.put(name, rates.getDouble(name));
//		}
		lr.setId(id);
		return lr;
	}

}
