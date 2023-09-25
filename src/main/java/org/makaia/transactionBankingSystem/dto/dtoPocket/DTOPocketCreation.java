package org.makaia.transactionBankingSystem.dto.dtoPocket;

import java.math.BigDecimal;

public class DTOPocketCreation {
    private Long accountNumber;
    private String name;
    private BigDecimal initialBalance;

    public DTOPocketCreation() {
    }

    public DTOPocketCreation(Long accountNumber, String name, BigDecimal initialBalance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.initialBalance = initialBalance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}
