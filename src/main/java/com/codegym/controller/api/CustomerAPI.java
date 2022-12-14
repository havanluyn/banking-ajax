package com.codegym.controller.api;
import com.codegym.model.Customer;
import com.codegym.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerAPI {
    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {

        List<Customer> customers = customerService.findAllByDeletedEquals(false);

        if (customers.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> create(@PathVariable Long customerId) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            try {
                throw new IOException("Customer ID not valid");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new ResponseEntity<>(customerOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {

        Optional<Customer> customerOptional = customerService.findByEmail(customer.getEmail());

        if (customerOptional.isPresent()) {
            try {
                throw new IOException("Email đã tồn tại");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        customer.setId(0L);
        customer.setBalance(BigDecimal.ZERO);
        Customer newCustomer = customerService.save(customer);

        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<Customer> update(@PathVariable Long customerId, @RequestBody Customer customer) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            try {
                throw new IOException("Customer ID not valid");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Customer cus = customerOptional.get();
        Optional<Customer> emailOptional = customerService.findByEmailAndIdIsNot(cus.getEmail(), customerId);

        if (emailOptional.isPresent()) {
            try {
                throw new IOException("Email đã tồn tại");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (customer.getBalance() != null) {
            cus.setBalance(customer.getBalance());
        }
        if (customer.getFullname() != null) {
            cus.setFullname(customer.getFullname());
        }
        if (customer.getEmail() != null) {
            cus.setEmail(customer.getEmail());
        }
        if (customer.getFullname() != null) {
            cus.setAddress(customer.getAddress());
        }
        cus.setId(customerId);
        Customer updatedCustomer = customerService.save(cus);

        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Customer> delete( @PathVariable Long customerId) {
        Customer customer= customerService.findById(customerId).get();
        customer.setDeleted(true);
        customerService.save(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
