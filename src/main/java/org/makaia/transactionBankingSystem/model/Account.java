package org.makaia.transactionBankingSystem.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    private Long accountNumber;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ManyToOne()
    private Person person;

    @OneToMany(mappedBy = "account" , cascade = CascadeType.ALL)
    private List<Pocket> pockets;


    public Account() {

    }

    public Account(Long accountNumber, BigDecimal balance, Person person) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.person = person;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
