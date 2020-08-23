package com.fdm.CryptoCurrency.apiTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import com.fdm.CryptoCurrency.api.CryptoCurrency;
import com.fdm.CryptoCurrency.api.CurrencyDetail;
import com.fdm.CryptoCurrency.controllers.CoinRestController;
import com.fdm.CryptoCurrency.exception.NotFoundCurrencyException;
import com.fdm.CryptoCurrency.exception.NotFoundPageException;
import com.fdm.CryptoCurrency.service.CoinService;

public class ControllerTest {
	
	
	private CoinService coinService;
	private CurrencyDetail mockCD;
	private CryptoCurrency mockCC;
	private CoinRestController coinRestController;
	
	
	@Before
	public void setUp() throws Exception{
		coinService = mock(CoinService.class);
		coinRestController = new CoinRestController(coinService);
		mockCD = mock(CurrencyDetail.class);
		mockCC = mock(CryptoCurrency.class);
	}
	
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void that_getCurrencyDetail_returns_cd() {
		when(coinService.getCurrencyDetail("bitcoin")).thenReturn(mockCD);
		CurrencyDetail cd = coinRestController.getCurrencyDetail("bitcoin");
		assertEquals(mockCD,cd);
	}
	
	@Test
	public void that_getAll_returns_all() throws NotFoundCurrencyException, NotFoundPageException {
		ArrayList<CryptoCurrency> mockCCs = new ArrayList<CryptoCurrency>();
		mockCCs.add(mockCC);
		when(coinService.getAll("usd","1")).thenReturn(mockCCs);
		ArrayList<CryptoCurrency> cryptoCurrencys = coinRestController.getAll("usd","1");
		assertEquals(cryptoCurrencys,mockCCs);
	}
	
	@Test(expected = NotFoundCurrencyException.class)
	public void that_getAll_notFoundCurrency() throws NotFoundCurrencyException, NotFoundPageException {
		coinRestController.getAll("abc","1");
		
	}
	
	@Test(expected = NotFoundPageException.class)
	public void that_getAll_notFoundPage() throws Exception {
		coinRestController.getAll("aud","11");

	}
	
	
}
