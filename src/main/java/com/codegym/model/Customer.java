package com.codegym.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private BigDecimal balance;
    @OneToMany
    private List<Deposit> deposits;

    @OneToMany
    private List<Transfer> senders;

    @OneToMany
    private List<Transfer> recipients;

    public Customer() {
    }

    public Customer(Long id, String fullname, String email, String phone, String address, BigDecimal balance) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
    }

    public Customer(String name, String email, String phone, String address, BigDecimal balance) {
        this.fullname = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
