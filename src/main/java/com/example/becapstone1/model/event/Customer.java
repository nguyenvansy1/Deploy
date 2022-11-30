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

    @Column(name = "customer_address")
    private String address;

    @Column(name = "customer_avatar")
    private String avatar;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_account_id")
    private Account account;

    public Customer() {
    }

    public Customer(Long id, String name, String address, Account account) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.account = account;
    }

    public Customer(String name, Account account,String avatar,String address) {
        this.name = name;
        this.account = account;
        this.avatar = avatar;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
