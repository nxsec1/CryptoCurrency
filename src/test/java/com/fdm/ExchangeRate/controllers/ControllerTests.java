package com.fdm.ExchangeRate.controllers;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fdm.CryptoCurrency.api.DataView;
import com.fdm.CryptoCurrency.controllers.DataViewController;

public class ControllerTests {

	private static DataViewController dvc;

	@BeforeClass
	public static void init() {
		dvc = new DataViewController();
	}

	@Test
	public void test_historical_date() {
		String PostData = "{\"startDate\":\"2020-01-01\",\"endDate\":\"2020-01-10\",\"wanted\":\"AUD,NZD,CNY\",\"base\":\"USD\"}";
		DataView result = dvc.getHistoricalData(PostData);
		assertTrue(result != null);
	}

	@Test
	public void test_latest_data() {

		DataView result = dvc.getLatest("USD");
		assertTrue(result != null);
	}

	@Test
	public void test_correlated_data() {
		String PostData = "{\"startDate\":\"2020-01-01\",\"endDate\":\"2020-01-10\",\"base\":\"USD\"}";
		DataView result = dvc.getCorrelations(PostData);
		assertTrue(result != null);
	}

}
