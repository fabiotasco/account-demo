
package io.fab.account.demo.exceptions;

import io.fab.account.demo.data.Transaction;

public class InsufficientFundsException extends RuntimeException {

	private static final long serialVersionUID = -3472807755066613865L;

	public InsufficientFundsException(final Transaction transaction) {
		super(String.format("Insufficient funds for transaction amount of %.2f for account %s",
				transaction.getAmount(),
				transaction.getAccount().getPan()));
	}

}
