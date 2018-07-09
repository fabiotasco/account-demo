
package io.fab.account.demo.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.fab.account.demo.canonicals.Error;
import io.fab.account.demo.canonicals.Response;
import io.fab.account.demo.exceptions.AccountNotFoundException;
import io.fab.account.demo.exceptions.InsufficientFundsException;
import io.fab.account.demo.exceptions.NonCancelableTransactionException;
import io.fab.account.demo.exceptions.TransactionAlreadyCanceledException;
import io.fab.account.demo.exceptions.TransactionNotFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response<?>> handleDefaultError(final Exception e) throws Exception {
		LOG.error("Handling default error...", e);
		return new ResponseEntity<>(new Response<>(buildInternalServerError(e)), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ AccountNotFoundException.class, TransactionNotFoundException.class })
	public ResponseEntity<Response<?>> handleNoResultException(final Exception e) {
		LOG.error("No result exception handler!", e);
		return new ResponseEntity<>(new Response<>(buildAccountNotFoundException(e)), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<Response<?>> handleInsufficientFundException(final Exception e) {
		LOG.error("Insufficient fund exception handler!", e);
		return new ResponseEntity<>(new Response<>(buildInsufficientFundException()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(NonCancelableTransactionException.class)
	public ResponseEntity<Response<Error>> handleNonCancelableTransactionException() {
		return new ResponseEntity<>(new Response<>(buildNonCancelableTransactionException()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(TransactionAlreadyCanceledException.class)
	public ResponseEntity<Response<Error>> handleTransactionAlreadyCanceledException(final Exception e) {
		return new ResponseEntity<>(new Response<>(buildTransactionAlreadyCanceledException(e)), HttpStatus.CONFLICT);
	}

	private Error buildInternalServerError(final Exception e) {
		return new Error.Builder().setCode(500).setMessage(e.getMessage()).build();
	}

	private Error buildAccountNotFoundException(final Exception e) {
		return new Error.Builder().setCode(404).setMessage(e.getMessage()).build();
	}

	private Error buildInsufficientFundException() {
		return new Error.Builder().setCode(409).setMessage("Saldo insuficiente!").build();
	}

	private Error buildNonCancelableTransactionException() {
		return new Error.Builder().setCode(410).setMessage("Transação não pode ser cancelada!").build();
	}

	private Error buildTransactionAlreadyCanceledException(final Exception e) {
		return new Error.Builder().setCode(411).setMessage(e.getMessage()).build();
	}

}
