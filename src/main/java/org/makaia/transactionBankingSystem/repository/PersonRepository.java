package org.makaia.transactionBankingSystem.repository;

import org.makaia.transactionBankingSystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
}
