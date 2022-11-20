package com.example.becapstone1.model.event;

import com.example.becapstone1.model.account.Account;

import javax.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "customer_name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_account_id")
    private Account account;

    public Customer() {
    }

    public Customer(Long id, String name, Account account) {
        this.id = id;
        this.name = name;
        this.account = account;
    }

    public Customer( String name, Account account) {
        this.name = name;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
