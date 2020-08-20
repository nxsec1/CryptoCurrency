package com.fdm.CryptoCurrency.apiTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fdm.CryptoCurrency.api.CryptoCurrency;
import com.fdm.CryptoCurrency.api.CurrencyDetail;
import com.fdm.CryptoCurrency.service.CoinService;


public class CoinServiceTest {
	
	private static CoinService coinService;
	
	@BeforeClass
	public static void init() {
		coinService = new CoinService();
	}
	
	
	@Test
	public void test_getCurrencyDetail_returns_cd() {
		CurrencyDetail cd = coinService.getCurrencyDetail("bitcoin");
		assertEquals("bitcoin",cd.getId());
	}
	
	@Test
	public void test_getAll_returns_ccs() {
		ArrayList<CryptoCurrency> cryptoCurrencys = coinService.getAll("aud","1");
		assertEquals("bitcoin",cryptoCurrencys.get(0).getId());
	}
	
}
