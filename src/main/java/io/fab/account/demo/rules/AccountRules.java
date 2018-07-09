
package io.fab.account.demo.rules;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.fab.account.demo.data.Account;
import io.fab.account.demo.data.Transaction;
import io.fab.account.demo.data.TransactionType;
import io.fab.account.demo.dto.AccountTransactionDto;
import io.fab.account.demo.exceptions.AccountNotFoundException;
import io.fab.account.demo.exceptions.TransactionAlreadyCanceledException;
import io.fab.account.demo.exceptions.TransactionNotFoundException;
import io.fab.account.demo.repositories.AccountRepository;
import io.fab.account.demo.repositories.TransactionRespository;

@Component
public class AccountRules {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRespository transactionRespository;

	@Autowired
	private TransactionCalculator calculator;

	public Double getBalance(final String pan) {
		final Account account = findAccount(pan);
		return account.getBalance();
	}

	public Transaction processTransaction(final AccountTransactionDto accountTransactionDto,
			final TransactionType transactionType) {

		final Account account = findAccount(accountTransactionDto.getPan());
		final Transaction transaction = new Transaction(transactionType, accountTransactionDto.getAmount(), account);

		calculator.calculate(transaction);
		transactionRespository.save(transaction);

		return transaction;
	}

	public List<Transaction> listTransaction(final String pan) {
		final Account account = findAccount(pan);
		return account.getTransactions();
	}

	public Transaction cancel(final Long transactionId) {
		try {
			final Transaction original = transactionRespository.findById(transactionId).get();
			if (isTransactionAlreadyCanceled(original)) {
				throw new TransactionAlreadyCanceledException(original);
			}
			final Transaction cancelTransaction =
					new Transaction(TransactionType.CANCEL, original.getAmount(), original.getAccount(), original);
			calculator.calculate(cancelTransaction);
			transactionRespository.save(cancelTransaction);

			return cancelTransaction;

		} catch (final NoSuchElementException e) {
			throw new TransactionNotFoundException(transactionId);
		}
	}

	private Account findAccount(final String pan) {
		final Account account = accountRepository.findByPan(pan);
		if (account == null) {
			throw new AccountNotFoundException(pan);
		}
		return account;
	}

	private boolean isTransactionAlreadyCanceled(final Transaction original) {
		final List<Transaction> transactions = transactionRespository.findByOriginal(original);
		return transactions.parallelStream()
				.filter(transaction -> transaction.getType() == TransactionType.CANCEL)
				.findFirst()
				.isPresent();
	}

}
