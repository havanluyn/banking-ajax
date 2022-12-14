package com.codegym.service.customer;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.model.Transfer;
import com.codegym.repository.CustomerRepository;
import com.codegym.repository.DepositRepository;
import com.codegym.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private TransferRepository transferRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    @Override
    public Optional<Customer> findByEmail(String email){
        return customerRepository.findByEmail(email);
    };
    public Optional<Customer> findByEmailAndIdIsNot(String email,Long id){
        return customerRepository.findByEmailAndIdIsNot(email,id);
    };
    @Override
    public List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLike(String fullName, String email, String phone) {
        return customerRepository.findAllByFullnameLikeOrEmailLikeOrPhoneLike(fullName, email, phone);
    }

    @Override
    public void deposit(Deposit deposit, Customer customer) {
        deposit.setId(0L);
        deposit.setCustomer(customer);
        depositRepository.save(deposit);
        BigDecimal newBalance = deposit.getTransactionAmount();
        incrementBalance(customer, newBalance);
    }

    @Override
    public void withdraw(Deposit deposit, Customer customer) {
        deposit.setId(0L);
        deposit.setCustomer(customer);
        depositRepository.save(deposit);
        BigDecimal newBalance = deposit.getTransactionAmount();
        reduceBalance(customer, newBalance);
    }

    @Override
    public void incrementBalance(Customer customer, BigDecimal balance) {
        BigDecimal newBalance = balance.add(customer.getBalance());
        customer.setBalance(newBalance);
        save(customer);
    }

    @Override
    public void reduceBalance(Customer customer, BigDecimal balance) {
        BigDecimal newBalance = customer.getBalance().subtract(balance);
        customer.setBalance(newBalance);
        save(customer);
    }

    @Override
    public List<Customer> findAllAndIdNotExists(Long id) {
        return customerRepository.findAllAndIdNotExists(id);
    }

    @Override
    public void transfer(Transfer transfer) {
        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal transactionAmount = transfer.getTransactionAmount();
        reduceBalance(transfer.getSender(), transactionAmount);
        incrementBalance(transfer.getRecipient(), transferAmount);
        transferRepository.save(transfer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer getById(Long id) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}