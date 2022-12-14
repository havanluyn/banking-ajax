package com.codegym.service.customer;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.model.Transfer;
import com.codegym.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer> {

    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLike(String fullName, String email, String phone);
    void deposit(Deposit deposit, Customer customer);
    void withdraw(Deposit deposit, Customer customer);
//     void incrementBalance(Customer customer, BigDecimal balance);
//    void reduceBalance(Customer customer,BigDecimal balance);
    List<Customer> findAllAndIdNotExists(Long id);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByEmailAndIdIsNot(String email,Long id);
    void transfer(Transfer transfer);
}