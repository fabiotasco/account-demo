
package io.fab.account.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.fab.account.demo.data.Transaction;

public interface TransactionRespository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByOriginal(Transaction original);

}
