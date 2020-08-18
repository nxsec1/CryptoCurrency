package com.fdm.CryptoCurrency.api;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrency {
	private String id;
	private String current_price;
	private String market_cap;
	private ArrayList<StatusUpdate> statusUpdates;
}
