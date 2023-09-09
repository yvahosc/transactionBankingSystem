package org.makaia.transactionBankingSystem.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    private Long id;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "phone", nullable = false)
    private int phone;
    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "person" , cascade = CascadeType.ALL)
    private List<Account> accounts;

    public Person() {
    }

    public Person(Long id, String firstName, String lastName, int phone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return firstName;
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
