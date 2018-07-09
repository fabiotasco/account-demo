
package io.fab.account.demo.exceptions;

public class TransactionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5473886915395332147L;

	public TransactionNotFoundException(final Long transactionId) {
		super(String.format("Transaction %d not found!", transactionId));
	}

}
