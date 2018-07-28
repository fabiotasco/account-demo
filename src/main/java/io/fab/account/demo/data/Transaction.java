
package io.fab.account.demo.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * Transação realizada na conta. Os tipos de transações permitidas estão definidas em {@link TransactionType}.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

	private static final long serialVersionUID = 8012352342434848715L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type", length = 2, nullable = false)
	private TransactionType type;

	@Column(name = "amount", precision = 10, scale = 2, nullable = false)
	private Double amount;

	@Column(name = "current_balance", precision = 10, scale = 2, nullable = false)
	private Double currentBalance;

	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE }, optional = false)
	@JoinColumn(name = "account_id")
	private Account account;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE }, optional = true)
	@JoinColumn(name = "original_transaction_id")
	private Transaction original;

	/** Somente JPA! */
	Transaction() {}

	/**
	 * Define o evento, tipo de transação, valor e o chip que está transacionando.
	 *
	 * @param type O tipo da transação.
	 * @param amount O valor da transação.
	 * @param account A conta que está transacionando.
	 */
	public Transaction(final TransactionType type, final Double amount, final Account account) {
		this.type = type;
		this.amount = amount;
		this.account = account;
		currentBalance = account.getBalance();
	}

	/**
	 * Define o evento, tipo de transação, valor e o chip que está transacionando.
	 *
	 * @param type O tipo da transação.
	 * @param amount O valor da transação.
	 * @param account A conta que está transacionando.
	 * @param original A transação original.
	 */
	public Transaction(final TransactionType type,
			final Double amount,
			final Account account,
			final Transaction original) {
		this(type, amount, account);
		this.original = original;
	}

	@PrePersist
	private void prePersist() {
		createdAt = new Date();
	}

	/**
	 * @return Id da entidade.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return O tipo da transação.
	 */
	public TransactionType getType() {
		return type;
	}

	/**
	 * @return O valor da transação.
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @return A data da transação.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return O conta transacionada.
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @return Transação original.
	 */
	public Transaction getOriginal() {
		return original;
	}

	/**
	 * @param original Transação original.
	 */
	public void setOriginal(final Transaction original) {
		this.original = original;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (amount == null ? 0 : amount.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Transaction other = (Transaction) obj;
		if (amount == null) {
			if (other.amount != null) {
				return false;
			}
		} else if (!amount.equals(other.amount)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [id="
				+ id
				+ ", type="
				+ type
				+ ", amount="
				+ amount
				+ ", currentBalance="
				+ currentBalance
				+ ", createdAt="
				+ createdAt
				+ ", account="
				+ account
				+ ", original="
				+ original
				+ "]";
	}

}
