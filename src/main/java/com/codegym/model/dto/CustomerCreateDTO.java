package com.codegym.model.dto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;


public class CustomerCreateDTO implements Validator {

    private Long id;

    @NotEmpty(message = "Full Name is required")
    private String fullname;
    @NotEmpty(message = "Email is required")
    private String email;
    private String phone;
    private String address;
    private String balance;

    public CustomerCreateDTO() {
    }

    public CustomerCreateDTO(Long id, String fullName, String email, String phone, String address, String balance) {
        this.id = id;
        this.fullname = fullName;
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

    public void setFullname(String fullName) {
        this.fullname = fullName;
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerCreateDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CustomerCreateDTO customerCreateDTO = (CustomerCreateDTO) target;

        String email = customerCreateDTO.getEmail();

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,6}$")) {
            errors.rejectValue("email", "email.matches", "Email not valid");
            return;
        }

    }
}
