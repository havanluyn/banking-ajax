package com.codegym.controller.api;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.model.dto.DepositCreateDTO;
import com.codegym.service.customer.ICustomerService;

import com.codegym.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deposit")
public class DepositAPI {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDepositService depositService;

    @GetMapping
    public ResponseEntity<List<Deposit>> getAllDeposits() {

        List<Deposit> deposits = depositService.findAll();

        if (deposits.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(deposits, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Deposit> doDeposit(@RequestBody DepositCreateDTO depositCreateDTO){

        Long id=Long.parseLong(depositCreateDTO.getCustomerId());
        Optional<Customer> customerOptional= customerService.findById(id);
        Customer customer = customerOptional.get();
        Deposit deposit=new Deposit();
        deposit.setId(0L);
        deposit.setCustomer(customer);
//        String string=depositCreateDTO.getTransactionAmount();
        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(depositCreateDTO.getTransactionAmount()));
        deposit.setTransactionAmount(transactionAmount);
        customerService.deposit(deposit,customer);
        return new ResponseEntity<>(deposit,HttpStatus.OK);
    }
}
