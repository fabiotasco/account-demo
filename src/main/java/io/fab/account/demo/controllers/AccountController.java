
package io.fab.account.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.fab.account.demo.canonicals.Response;
import io.fab.account.demo.data.Transaction;
import io.fab.account.demo.data.TransactionType;
import io.fab.account.demo.dto.AccountTransactionDto;
import io.fab.account.demo.rules.AccountRules;

@RestController
@RequestMapping("/account")
@ResponseStatus(HttpStatus.OK)
public class AccountController {

	@Autowired
	private AccountRules accountRules;

	@GetMapping("/pan/{pan}/balance")
	public Response<Double> getBalance(@PathVariable final String pan) {
		final Double balance = accountRules.getBalance(pan);
		return new Response<>(balance);
	}

	@PostMapping("/purchase")
	public Response<Transaction> purchase(@RequestBody final AccountTransactionDto accountTransactionDto) {
		final Transaction transaction =
				accountRules.processTransaction(accountTransactionDto, TransactionType.PURCHASE);
		return new Response<>(transaction);
	}

	@PostMapping("/credit")
	public Response<Transaction> credit(@RequestBody final AccountTransactionDto accountTransactionDto) {
		final Transaction transaction = accountRules.processTransaction(accountTransactionDto, TransactionType.CREDIT);
		return new Response<Transaction>(transaction);
	}

	@GetMapping("/pan/{pan}/transactions")
	public Response<List<Transaction>> listTransactions(@PathVariable final String pan) {
		final List<Transaction> transactions = accountRules.listTransaction(pan);
		return new Response<>(transactions);
	}

	@DeleteMapping("/transaction/{transactionId}/cancel")
	public Response<Transaction> cancel(@PathVariable final Long transactionId) {
		final Transaction transaction = accountRules.cancel(transactionId);
		return new Response<Transaction>(transaction);
	}

}
