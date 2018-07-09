
package io.fab.account.demo.exceptions;

import io.fab.account.demo.data.Transaction;

public class NonCancelableTransactionException extends RuntimeException {

	private static final long serialVersionUID = -835980747438226720L;

	public NonCancelableTransactionException(final Transaction transaction) {
		super(String.format("Original transaction is missing: " + transaction));
	}

}
