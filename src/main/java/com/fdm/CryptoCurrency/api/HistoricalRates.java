package com.fdm.CryptoCurrency.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONObject;

public class HistoricalRates extends DataView {

	private Map<String, Map<String, Double>> linearDateValue;
	private Map<String, Map<String, Double>> logarithmicDateValue;

	public HistoricalRates() {
		super();
		this.linearDateValue = new TreeMap<>();
		this.logarithmicDateValue = new TreeMap<>();
	}

	public Map<String, Map<String, Double>> getLinearDateValue() {
		return linearDateValue;
	}

	public void setLinearDateValue(Map<String, Map<String, Double>> linearDateValue) {
		this.linearDateValue = linearDateValue;
	}

	public Map<String, Map<String, Double>> getLogarithmicDateValue() {
		return logarithmicDateValue;
	}

	public void setLogarithmicDateValue(Map<String, Map<String, Double>> logarithmicDateValue) {
		this.logarithmicDateValue = logarithmicDateValue;
	}
	

	@Override
	public String toString() {
		return "HistoricalRates [logarithmicDateValue=" + logarithmicDateValue + "]";
	}

	@Override
	public DataView processData(JSONObject obj) {

		HistoricalRates hr = new HistoricalRates();
		Map<String, Map<String, Double>> minMax = new TreeMap<>();
		JSONObject rates = (JSONObject) obj.get("rates");
		for (String date : rates.keySet()) {
			JSONObject singleDate = (JSONObject) rates.get(date);
			Set<String> rateNames = singleDate.keySet();
			Map<String, Double> innerMap = new TreeMap<>();
			for (String rateName : rateNames) {
				System.out.println(singleDate.getDouble(rateName));
				if (minMax.get(rateName) == null) {
					minMax.put(rateName, new TreeMap<>());
				}
				if (minMax.get(rateName).get("min") == null) {
					minMax.get(rateName).put("min", singleDate.getDouble(rateName));
				}
				if (minMax.get(rateName).get("max") == null) {
					minMax.get(rateName).put("max", singleDate.getDouble(rateName));
				}
				
				if (minMax.get(rateName).get("min") > singleDate.getDouble(rateName)) {
					minMax.get(rateName).put("min", singleDate.getDouble(rateName));
				}
				if (minMax.get(rateName).get("max") < singleDate.getDouble(rateName)) {
					minMax.get(rateName).put("max", singleDate.getDouble(rateName));
				}
				
				innerMap.put(date, singleDate.getDouble(rateName));
				if (hr.linearDateValue.get(rateName) != null) {
					hr.linearDateValue.get(rateName).put(date, singleDate.getDouble(rateName));
//					hr.logarithmicDateValue.get(rateName).put(date, Math.log(singleDate.getDouble(rateName)));
				} else {
					Map<String, Double> newLinearMap = new TreeMap<>();
//					Map<String, Double> newLogMap = new TreeMap<>();
					newLinearMap.put(date, singleDate.getDouble(rateName));
//					newLogMap.put(date, Math.log(singleDate.getDouble(rateName)));
					hr.linearDateValue.put(rateName, newLinearMap);
//					hr.logarithmicDateValue.put(rateName, newLogMap);
//
				}
			}
		}
		
		for (String rateName: hr.linearDateValue.keySet()) {
			double rateMax = minMax.get(rateName).get("max");
			double rateMin = minMax.get(rateName).get("min");
		
			for (String date: hr.linearDateValue.get(rateName).keySet()) {
				double value = 2*((hr.linearDateValue.get(rateName).get(date) - rateMin) / (rateMax - rateMin)) -1;
				if (hr.logarithmicDateValue.get(rateName) == null) {
					Map<String, Double> innerMap = new TreeMap<>();
					innerMap.put(date, value);
					hr.logarithmicDateValue.put(rateName, innerMap);
				} else {
					hr.logarithmicDateValue.get(rateName).put(date, value);
				}
			}
		}
		return hr;
	}

}
