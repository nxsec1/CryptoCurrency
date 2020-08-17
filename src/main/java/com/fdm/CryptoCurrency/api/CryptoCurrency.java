package com.fdm.CryptoCurrency.api;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoCurrency {
	private String id;
	private String current_price;
	private String market_cap;
	private ArrayList<StatusUpdate> statusUpdates;
}
