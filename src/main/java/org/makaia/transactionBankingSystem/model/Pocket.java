package org.makaia.transactionBankingSystem.model;

import javax.persistence.*;

@Entity
@Table(name = "pocket")
public class Pocket {

    @Id
    private String number_pocket;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "balance", nullable = false)
    private double balance;
    @ManyToOne()
    private Account account;

    public Pocket() {
    }

    public String getNumber_pocket() {
        return number_pocket;
    }

    public void setNumber_pocket(String number_pocket) {
        this.number_pocket = number_pocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
