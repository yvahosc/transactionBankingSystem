package org.makaia.transactionBankingSystem.dto.dtoPocket;

import java.math.BigDecimal;

public class DTOPocketTransfer {
    private Long accountNumber;
    private Long pocketNumber;
    private BigDecimal amount;

    public DTOPocketTransfer() {
    }

    public DTOPocketTransfer(Long accountNumber, Long pocketNumber, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.pocketNumber = pocketNumber;
        this.amount = amount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getPocketNumber() {
        return pocketNumber;
    }

    public void setPocketNumber(Long pocketNumber) {
        this.pocketNumber = pocketNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
