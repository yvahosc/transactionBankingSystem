package org.makaia.transactionBankingSystem.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pocket")
public class Pocket {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long numberPocket;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @ManyToOne()
    private Account account;

    public Pocket() {
    }
    public Pocket(String name, BigDecimal amount, Account account) {
        this.name = name;
        this.amount = amount;
        this.account = account;
    }

    public Long getNumberPocket() {
        return numberPocket;
    }

    public void setNumber_pocket(Long numberPocket) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
