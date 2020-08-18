package com.fdm.CryptoCurrency.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusUpdate {
	private String title;
	private String description;
	private String created_at;
}
