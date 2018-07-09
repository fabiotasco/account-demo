
package io.fab.account.demo.data;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionType {

	CREDIT("credit"),
	PURCHASE("purchase"),
	CANCEL("cancel");

	private String description;

	TransactionType(final String description) {
		this.description = description;
	}

	@JsonValue
	public String getDescription() {
		return description;
	}

}
