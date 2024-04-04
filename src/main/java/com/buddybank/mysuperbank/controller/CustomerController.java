package com.buddybank.mysuperbank.controller;

import com.buddybank.mysuperbank.model.Account;
import com.buddybank.mysuperbank.model.Customer;
import com.buddybank.mysuperbank.model.Transaction;
import com.buddybank.mysuperbank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/test")
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers";
    }
    @GetMapping("/new")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "addCustomer";
    }

    @PostMapping("/new")
    public String createCustomer(Customer customer, Model model) {
        customerService.createCustomer(customer);
        return "redirect:/api/customers/test"; // Redirect to the list of customers after adding a new one
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

    @GetMapping("/{customerId}/accounts")
    public String viewCustomerAccounts(@PathVariable Long customerId, Model model) {
        List<Account> accounts = customerService.getAccountsForCustomer(customerId);
        model.addAttribute("accounts", accounts);
        return "customerAccounts"; // Assuming you have a Thymeleaf template named 'customerAccounts.html'
    }
}