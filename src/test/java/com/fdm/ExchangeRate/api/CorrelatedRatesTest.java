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

import com.fdm.CryptoCurrency.api.CorrelatedRates;
import com.fdm.CryptoCurrency.api.DataView;

public class CorrelatedRatesTest {


	private static final String VALID_URL="https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-01-04&base=AUD";
	private static final String INVALID_URL="https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-01-04&base=NOTREAL";
	private static CorrelatedRates cr;

	@BeforeClass
	public static void init() {
		cr = new CorrelatedRates();
	}
	
	
	@Test
	public void test_can_get_latest_data_for_AUD() {
		DataView data = cr.retrieveData(VALID_URL);
		assertFalse(data == null);
	}


	@Test(expected = JSONException.class)
	public void test_fails_unsupported_base() {
		DataView data = cr.retrieveData(INVALID_URL);
		assertTrue(data == null);
	}

}
