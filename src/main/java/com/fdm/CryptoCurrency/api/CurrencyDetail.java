package com.fdm.CryptoCurrency.api;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDetail{
	private String id;
	private String symbol;
	private String name;
	private String market_cap;
	private String genesis_date;
	private String last_update;
	private HashMap<String, String> current_price;
	private HashMap<String, String> price_percentage_change_in_24hr;
	private HashMap<String, String> lastWeek_price;



}
