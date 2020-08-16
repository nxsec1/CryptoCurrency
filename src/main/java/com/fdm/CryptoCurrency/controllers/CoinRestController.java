package com.fdm.CryptoCurrency.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.CryptoCurrency.service.CoinService;
import com.fdm.CryptoCurrency.api.CurrencyDetail;

@RestController
public class CoinRestController {
	private CoinService coinService;
	


	@Autowired
	public CoinRestController(CoinService coinService) {
		this.coinService = coinService;
	}


	@GetMapping(value = "/coin/{id}")
	public CurrencyDetail getCurrencyDetail(@PathVariable String id) {

		CurrencyDetail currencyDetail = null;
		
		currencyDetail = coinService.getCurrencyDetail(id);
		return currencyDetail;
	}
	
}
