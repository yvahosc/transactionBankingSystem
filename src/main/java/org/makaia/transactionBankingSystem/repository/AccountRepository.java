package org.makaia.transactionBankingSystem.repository;

import org.makaia.transactionBankingSystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
