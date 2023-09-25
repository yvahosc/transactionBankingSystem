package org.makaia.transactionBankingSystem.repository;

import org.makaia.transactionBankingSystem.model.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PocketRepository extends JpaRepository<Pocket, Long> {
    public List<Pocket> findByAccountAccountNumber(Long accountNumber);
    public Pocket findByNumberPocketAndAccountAccountNumber(Long numberPocket, Long accountNumber);
}
