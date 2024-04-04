package com.buddybank.mysuperbank.controller;

import com.buddybank.mysuperbank.model.Account;
import com.buddybank.mysuperbank.model.Customer;
import com.buddybank.mysuperbank.service.AccountService;
import com.buddybank.mysuperbank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    @Autowired
    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping("/customers/{customerId}/accounts/create")
    public String showCreateAccountForm(@PathVariable Long customerId, Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        model.addAttribute("customerId", customerId);
        return "createAccount";
    }

    @PostMapping("/customers/{customerId}/accounts/create")
    public String createAccount(@PathVariable Long customerId, @ModelAttribute("account") Account account, Model model) {
        Customer customer = customerService.getCustomerById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));
        account.setCustomer(customer);
        accountService.createAccount(account);
        return "redirect:/customers/" + customerId + "/accounts";
    }
}