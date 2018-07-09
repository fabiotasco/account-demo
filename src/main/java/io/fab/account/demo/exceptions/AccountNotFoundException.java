
package io.fab.account.demo.exceptions;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6690076227930259370L;

	public AccountNotFoundException(final String pan) {
		super(String.format("Account %s not found!", pan));
	}

}
