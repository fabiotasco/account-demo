
package io.fab.account.demo.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Conta vinculada a um PAN.
 */
@Entity
@Table(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1441257309681756510L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "pan", unique = true, length = 19, nullable = false)
	private String pan;

	@Column(name = "balance", precision = 14, scale = 2, nullable = false)
	private Double balance;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", nullable = false)
	private AccountStatus status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account", orphanRemoval = true)
	private List<Transaction> transactions;

	/** Somente JPA! */
	Account() {}

	/**
	 * Define PAN e saldo inicial da conta.
	 *
	 * @param pan Personal Account Number, também conhecido como o número do cartão.
	 * @param balance Saldo da conta.
	 */
	public Account(final String pan, final Double balance) {
		this.pan = pan;
		this.balance = balance;
		status = AccountStatus.ACTIVE;
	}

	/**
	 * @return Id da entidade.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return O número do chip embarcado (PAN = Personal Account Number).
	 */
	public String getPan() {
		return pan;
	}

	/**
	 * @return O saldo do chip embarcado.
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * @param balance O saldo do chip embarcado.
	 */
	public void setBalance(final Double balance) {
		this.balance = balance;
	}

	/**
	 * @return O status da conta.
	 */
	public AccountStatus getStatus() {
		return status;
	}

	/**
	 * @return Transações realizadas nessa conta.
	 */
	public List<Transaction> getTransactions() {
		return transactions;
	}

}
