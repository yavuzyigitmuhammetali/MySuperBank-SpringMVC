package com.buddybank.mysuperbank.controller;

import com.buddybank.mysuperbank.model.Account;
import com.buddybank.mysuperbank.model.Customer;
import com.buddybank.mysuperbank.model.Transaction;
import com.buddybank.mysuperbank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        return customerService.updateCustomer(id, customerDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    @PostMapping("/{customerId}/accounts")
    public Account createAccountForCustomer(@PathVariable Long customerId, @RequestBody Account account) {
        return customerService.createAccountForCustomer(customerId, account);
    }

    @PutMapping("/accounts/{accountId}")
    public Account updateAccount(@PathVariable Long accountId, @RequestBody Account accountDetails) {
        return customerService.updateAccount(accountId, accountDetails);
    }

    @DeleteMapping("/accounts/{accountId}")
    public void deleteAccount(@PathVariable Long accountId) {
        customerService.deleteAccount(accountId);
    }

    @PostMapping("/accounts/{accountId}/transactions")
    public Transaction createTransactionForAccount(@PathVariable Long accountId, @RequestBody Transaction transaction) {
        return customerService.createTransactionForAccount(accountId, transaction);
    }
}