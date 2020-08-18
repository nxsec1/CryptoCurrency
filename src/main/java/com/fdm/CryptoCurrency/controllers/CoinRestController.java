package com.fdm.CryptoCurrency.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.CryptoCurrency.service.CoinService;
import com.fdm.CryptoCurrency.api.CryptoCurrency;
import com.fdm.CryptoCurrency.api.CurrencyDetail;
import com.fdm.CryptoCurrency.exception.NotFoundCurrencyException;
import com.fdm.CryptoCurrency.exception.NotFoundPageException;

@RestController
public class CoinRestController {
	private CoinService coinService;
	


	@Autowired
	public CoinRestController(CoinService coinService) {
		this.coinService = coinService;
	}
	
	@GetMapping(value = "coins/market")
	public ArrayList<CryptoCurrency> getAll(@RequestParam(defaultValue = "usd",name="currency") String currency, @RequestParam(defaultValue = "1",name="page") String page) throws NotFoundCurrencyException, NotFoundPageException{
		List<String> avaliableCurrencies = new ArrayList<>(Arrays.asList("jpy", "aud","usd"));
		if(!avaliableCurrencies.contains(currency.toLowerCase())) {
			throw new NotFoundCurrencyException("Currency Not Found!");
		}
		if(Integer.parseInt(page)>10 || Integer.parseInt(page)<1) {
			throw new NotFoundPageException();
		}
		ArrayList<CryptoCurrency> cryptoCurrencys = coinService.getAll(currency,page);
		return cryptoCurrencys;
	}
	
	
	@GetMapping(value = "/coins/{id}")
	public CurrencyDetail getCurrencyDetail(@PathVariable String id) {
		CurrencyDetail currencyDetail = null;
		currencyDetail = coinService.getCurrencyDetail(id);
		return currencyDetail;
	}	
	
	@ExceptionHandler(NotFoundCurrencyException.class)
	public String notFoundCurrency() {
		return "Currency Not Found! \nThe availiable currencies are JPY ,AUD , USD. \nTry http://localhost:8080/coins/market instead. \nThanks!";
	}
	
	@ExceptionHandler(NotFoundPageException.class)
	public String notFoundPage() {
		return "Page Not Found! \nThe availiable page numbers are [1,10] \nTry http://localhost:8080/coins/market instead. \nThanks!";
	}
	
}
