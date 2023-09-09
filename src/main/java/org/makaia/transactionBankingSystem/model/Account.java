package org.makaia.transactionBankingSystem.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {

    @Id
    private Long accountNumber;
    @Column(name = "balance", nullable = false)
    private double balance;

    @ManyToOne()
    private Person person;

    @OneToMany(mappedBy = "account" , cascade = CascadeType.ALL)
    private List<Pocket> pockets;


    public Account() {

    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
