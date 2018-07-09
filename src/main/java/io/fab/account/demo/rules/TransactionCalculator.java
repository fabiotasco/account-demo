
package io.fab.account.demo.rules;

import org.springframework.stereotype.Component;

import io.fab.account.demo.data.Transaction;
import io.fab.account.demo.data.TransactionType;
import io.fab.account.demo.exceptions.InsufficientFundsException;
import io.fab.account.demo.exceptions.NonCancelableTransactionException;

@Component
public class TransactionCalculator {

	public void calculate(final Transaction transaction) {
		switch (transaction.getType()) {
			case PURCHASE:
				purchase(transaction);
				break;
			case CREDIT:
				credit(transaction);
				break;
			case CANCEL:
				cancel(transaction);
				break;
			default:
				throw new UnsupportedOperationException("Unknown transaction operation: " + transaction.getType());
		}
	}

	private void purchase(final Transaction transaction) {
		final Double balance = transaction.getAccount().getBalance();
		final Double amount = transaction.getAmount();

		if (balance.compareTo(amount) == -1) {
			throw new InsufficientFundsException(transaction);
		}
		transaction.getAccount().setBalance(balance.doubleValue() - amount.doubleValue());
	}

	private void credit(final Transaction transaction) {
		final Double balance = transaction.getAccount().getBalance();
		final Double amount = transaction.getAmount();

		transaction.getAccount().setBalance(balance.doubleValue() + amount.doubleValue());
	}

	private void cancel(final Transaction transaction) {
		if (transaction.getOriginal() == null || transaction.getOriginal().getType() == TransactionType.CANCEL) {
			throw new NonCancelableTransactionException(transaction);
		}
		switch (transaction.getOriginal().getType()) {
			case PURCHASE:
				credit(transaction);
				break;
			case CREDIT:
				purchase(transaction);
				break;
			default:
				throw new UnsupportedOperationException(String.format("Unable to cancel transaction: " + transaction));
		}
	}

}
