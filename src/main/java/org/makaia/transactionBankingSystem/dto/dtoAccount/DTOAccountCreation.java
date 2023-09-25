package org.makaia.transactionBankingSystem.dto.dtoAccount;

import org.makaia.transactionBankingSystem.model.Person;

import java.math.BigDecimal;

public class DTOAccountCreation {
    private Person owner;
    private BigDecimal initialBalance;

    public DTOAccountCreation() {
    }

    public DTOAccountCreation(Person owner, BigDecimal initialBalance) {
        this.owner = owner;
        this.initialBalance = initialBalance;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}
