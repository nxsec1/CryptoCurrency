package com.fdm.ExchangeRate.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.h2.engine.SysProperties;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdm.CryptoCurrency.api.DataView;
import com.fdm.CryptoCurrency.api.LatestRates;

public class LatestRatesTest {

	private static final String NOT_REAL = "NOTReal";
	private static final String AUD = "AUD";
	private static final String VALID_URL="https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-01-04&base=AUD";
	private static final String INVALID_URL="https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-01-04&base=NOTREAL";
	private static final String LATEST="https://api.exchangeratesapi.io/latest";
	private static LatestRates lr;

	@BeforeClass
	public static void init() {
		lr = new LatestRates();
	}
	
	@Test
	public void test_can_get_latest_data_for_AUD() {
	
		DataView data = lr.retrieveData(LATEST);
		assertTrue(data != null);
	}

	@Test(expected = JSONException.class)
	public void test_fails() {
		DataView data = lr.retrieveData(INVALID_URL);
		
	}

}
