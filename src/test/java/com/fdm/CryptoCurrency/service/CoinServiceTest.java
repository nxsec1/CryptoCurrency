package com.fdm.CryptoCurrency.service;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;


public class CoinServiceTest {
	
	private static CoinService coinService;
	
	@BeforeClass
	public static void init() {
		coinService = new CoinService();
	}
	
	@Test
	public void test_formatDate_returns_AustralianDate() {
		String formattedDate = coinService.formatDate("2020-08-18");
		assertEquals("18-08-2020",formattedDate);
	}
	
	
}
