package com.fdm.CryptoCurrency.controllers;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.CryptoCurrency.api.CorrelatedRates;
import com.fdm.CryptoCurrency.api.DataView;
import com.fdm.CryptoCurrency.api.HistoricalRates;
import com.fdm.CryptoCurrency.api.LatestRates;

@RestController
public class DataViewController {

	private HistoricalRates hr;
	private LatestRates lr;
	private CorrelatedRates cr;

	public DataViewController() {
		super();
		this.hr = new HistoricalRates();
		this.lr = new LatestRates();
		this.cr = new CorrelatedRates();
	}

	//Needs to be POST to have a req body
	// Karen needs to edit this to suit what she wants to do
	@RequestMapping(value="/historical",method={RequestMethod.POST,RequestMethod.PUT})
	public DataView getHistoricalData(@RequestBody String data) {
		
		System.out.println(data);
		HistoricalRates historicalData = null;
		JSONObject req = new JSONObject(data);
		String startDate = req.getString("startDate");
		String endDate = req.getString("endDate");
		JSONArray wanted = req.getJSONArray("wanted");
		String base = req.getString("base");

		String wantedString = wanted.join(",").replaceAll("\"", "");
		String url = "https://api.exchangeratesapi.io/history?start_at=" + startDate + "&end_at=" + endDate
				+ "&symbols=" + wantedString + "&base=" + base;
		System.out.println(url);
		historicalData = (HistoricalRates) hr.retrieveData(url);
		System.out.println(historicalData);
		return historicalData;

	}

	
	@GetMapping(value = "/coin/{id}")
	public DataView getLatest(@PathVariable String id) {

		LatestRates latestRates = null;

		String url = "https://api.coingecko.com/api/v3/coins/" + id;

		latestRates = (LatestRates) lr.retrieveData(url);
		return latestRates;

	}

	@PostMapping(value = "/correlations")
	public CorrelatedRates getCorrelations(@RequestBody String data) {

		
		
		CorrelatedRates correlations = null;
		JSONObject req = new JSONObject(data);
		
		String startDate = req.getString("startDate");
		startDate = startDate.substring(0,10);
		String endDate = req.getString("endDate");
		endDate = endDate.substring(0,10);
		String base = req.getString("base");
		System.out.println(startDate + " " +endDate + " " + base);
		String url = "https://api.exchangeratesapi.io/history?start_at=" + startDate + "&end_at=" + endDate + "&base="
				+ base;
		correlations = (CorrelatedRates) cr.retrieveData(url);
		
		return correlations;
	}
	
	@GetMapping(value = "/correlations")
	public CorrelatedRates getDefaultCorrelations() {

		CorrelatedRates correlations = null;
//		JSONObject req = new JSONObject(data);

//		String startDate = req.getString("startDate");
//		String endDate = req.getString("endDate");
//		String base = req.getString("base");

		String url = "https://api.exchangeratesapi.io/history?start_at=" + "2019-01-01" + "&end_at=" + "2019-12-31" + "&base="
				+ "USD";
		correlations = (CorrelatedRates) cr.retrieveData(url);
		
		return correlations;
	}
	
}
