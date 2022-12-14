package com.codegym.controller.api;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.model.dto.DepositCreateDTO;
import com.codegym.service.customer.ICustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/deposit")
public class DepositAPI {
    @Autowired
    private ICustomerService customerService;

    @PostMapping
    public ResponseEntity<?> doDeposit(@RequestBody DepositCreateDTO depositCreateDTO){

        Long id=Long.parseLong(depositCreateDTO.getCustomerId());
        Optional<Customer> customerOptional= customerService.findById(id);
        Customer customer = customerOptional.get();
        Deposit deposit=new Deposit();
        deposit.setId(0L);
        deposit.setCustomer(customer);
        String string=depositCreateDTO.getTransactionAmount();
        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(depositCreateDTO.getTransactionAmount()));
        deposit.setTransactionAmount(transactionAmount);
        customerService.deposit(deposit,customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
