
package io.fab.account.demo.exceptions;

import io.fab.account.demo.data.Transaction;

public class TransactionAlreadyCanceledException extends RuntimeException {

	private static final long serialVersionUID = -6209643962855087336L;

	public TransactionAlreadyCanceledException(final Transaction transaction) {
		super(String.format("Transaction with id %d already canceled!", transaction.getId()));
	}

}
