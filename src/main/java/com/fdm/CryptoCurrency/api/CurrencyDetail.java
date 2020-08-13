package com.fdm.CryptoCurrency.api;

import java.util.Map;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class CurrencyDetail extends DataView {
	private String id;
	private Map<String,Double> market_cap;

	@Override
	public DataView processData(JSONObject obj) {
		CurrencyDetail lr = new CurrencyDetail();
		String id = (String) obj.get("id");
//		JSONObject rates = (JSONObject) obj.get("market_cap");
//		for (String name : rates.keySet()) {
//			lr.market_cap.put(name, rates.getDouble(name));
//		}
		lr.setId(id);
		return lr;
	}

}
