package com.fdm.CryptoCurrency.apiTest;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.fdm.CryptoCurrency.api.CurrencyDetail;
import com.fdm.CryptoCurrency.controllers.CoinRestController;
import com.fdm.CryptoCurrency.service.CoinService;

public class ControllerTest {
	

	private CoinService coinService;
	private CurrencyDetail mockCD;

	private CoinRestController coinRestController;
	
	
	@Before
	public void setUp() throws Exception{
		coinService = mock(CoinService.class);
		coinRestController = new CoinRestController(coinService);
		mockCD = mock(CurrencyDetail.class);
	}
	
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void that_getCurrencyDetail_returns_cd() {
		
		when(coinRestController.getCurrencyDetail("bitcoin")).thenReturn(mockCD);
		
		CurrencyDetail cd = coinRestController.getCurrencyDetail("bitcoin");
	}
	
}
