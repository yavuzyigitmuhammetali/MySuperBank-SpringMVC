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
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
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
        return "redirect:/customers/"; // Redirect to the list of customers after adding a new one
    }

    @GetMapping("/{customerId}/accounts")
    public String viewCustomerAccounts(@PathVariable Long customerId, Model model) {
        List<Account> accounts = customerService.getAccountsForCustomer(customerId);
        model.addAttribute("accounts", accounts);
        return "customerAccounts"; // Assuming you have a Thymeleaf template named 'customerAccounts.html'
    }

    @GetMapping("/edit/{id}")
    public String showEditCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        model.addAttribute("customer", customer);
        return "editCustomer";
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, Customer customer, Model model) {
        customerService.updateCustomer(id, customer);
        return "redirect:/customers/"; // Adjust the redirect as needed
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers/"; // Adjust the redirect as needed
    }
}