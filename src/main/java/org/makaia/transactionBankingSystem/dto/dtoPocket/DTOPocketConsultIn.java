package org.makaia.transactionBankingSystem.dto.dtoPocket;

import java.math.BigDecimal;

public class DTOPocketConsultIn {
    private Long numberPocket;
    private String name;
    private BigDecimal amount;

    public DTOPocketConsultIn() {
    }

    public DTOPocketConsultIn(Long numberPocket, String name, BigDecimal amount) {
        this.numberPocket = numberPocket;
        this.name = name;
        this.amount = amount;
    }

    public Long getNumberPocket() {
        return numberPocket;
    }

    public void setNumberPocket(Long numberPocket) {
        this.numberPocket = numberPocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
