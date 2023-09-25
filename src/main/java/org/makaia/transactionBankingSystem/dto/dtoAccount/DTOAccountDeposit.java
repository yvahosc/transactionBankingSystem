package org.makaia.transactionBankingSystem.dto.dtoAccount;

import java.math.BigDecimal;

public class DTOAccountDeposit {
    private BigDecimal amount;

    public DTOAccountDeposit() {
    }

    public DTOAccountDeposit(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
