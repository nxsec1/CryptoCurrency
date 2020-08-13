package com.fdm.CryptoCurrency.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class CorrelatedRates extends DataView {

	@JsonIgnore
	private PearsonsCorrelation pc;
	private Map<String, Map<String, Double>> currencyRates;

	public CorrelatedRates() {
		super();
		this.pc = new PearsonsCorrelation();
		this.currencyRates = new TreeMap<>();
	}

	public PearsonsCorrelation getPc() {
		return pc;
	}

	public void setPc(PearsonsCorrelation pc) {
		this.pc = pc;
	}

	public Map<String, Map<String, Double>> getCurrencyRates() {
		return currencyRates;
	}

	public void setCurrencyRates(Map<String, Map<String, Double>> currencyRates) {
		this.currencyRates = currencyRates;
	}

	@Override
	public DataView processData(JSONObject obj) {

		CorrelatedRates cr = new CorrelatedRates();
		Map<String, List<Double>> currencyRatesData = new TreeMap<String, List<Double>>();
		JSONObject dates = (JSONObject) obj.get("rates");

		// Put daily rates of each rate to a single key
		for (String date : dates.keySet()) {
//			System.out.println(dates.get(date));
			JSONObject rates = dates.getJSONObject(date);
			for (String rateName : rates.keySet()) {
				double dayRate = (double) rates.get(rateName);
				if (currencyRatesData.containsKey(rateName)) {
					currencyRatesData.get(rateName).add(dayRate);
				} else {
					currencyRatesData.put(rateName, new ArrayList<Double>(Arrays.asList(dayRate)));
				}
			}
		}
		for (String outerRateName : currencyRatesData.keySet()) {
			double[] outerRateRates = new double[currencyRatesData.get(outerRateName).size()];

			for (int i = 0; i < outerRateRates.length; i++) {
				outerRateRates[i] = currencyRatesData.get(outerRateName).get(i).doubleValue();
			}
			Map<String, Double> outerRate = new TreeMap<>();
			for (String innerRateName : currencyRatesData.keySet()) {
				double[] innerRateRates = new double[currencyRatesData.get(innerRateName).size()];

				for (int i = 0; i < outerRateRates.length; i++) {
					innerRateRates[i] = currencyRatesData.get(innerRateName).get(i).doubleValue();
				}
				// add correlations
				double correlation = pc.correlation(outerRateRates, innerRateRates);
				if (!Double.isNaN(correlation)) {
					outerRate.put(innerRateName, (Math.round(correlation * 100.0) / 100.0));

				}
//				System.out.println(correlation+ " " +innerRateName +" " + outerRateName
			}
//			System.out.println(outerRateName + " " + outerRate);
//			if (outerRateName.equals(base))
			if (outerRate.size() > 0) {
				cr.currencyRates.put(outerRateName, outerRate);
			}
		}
//		System.out.println(rateCorrelations);
		return cr;
	}

	@Override
	public String toString() {
		return "CorrelatedRates [, currencyRates=" + currencyRates + "]";
	}

}
