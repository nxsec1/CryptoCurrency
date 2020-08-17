package com.fdm.CryptoCurrency.controllers;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.CryptoCurrency.service.CoinService;
import com.fdm.CryptoCurrency.api.CryptoCurrency;
import com.fdm.CryptoCurrency.api.CurrencyDetail;

@RestController
public class CoinRestController {
	private CoinService coinService;
	


	@Autowired
	public CoinRestController(CoinService coinService) {
		this.coinService = coinService;
	}
	
	@GetMapping(value = "coins/market")
	public ArrayList<CryptoCurrency> getAll(@RequestParam(defaultValue = "usd") String currency){
		
		ArrayList<CryptoCurrency> cryptoCurrencys = coinService.getAll(currency);
		
		return cryptoCurrencys;
	}
	
	
	@GetMapping(value = "/coins/{id}")
	public CurrencyDetail getCurrencyDetail(@PathVariable String id) {

		CurrencyDetail currencyDetail = null;
		
		currencyDetail = coinService.getCurrencyDetail(id);
		return currencyDetail;
	}
	
	
	
}
