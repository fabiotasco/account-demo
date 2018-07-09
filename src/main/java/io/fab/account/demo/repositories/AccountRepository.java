
package io.fab.account.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.fab.account.demo.data.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Account findByPan(String pan);

}
