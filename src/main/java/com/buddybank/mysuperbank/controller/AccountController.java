package com.buddybank.mysuperbank.controller;

import com.buddybank.mysuperbank.dto.TransferRequest;
import com.buddybank.mysuperbank.model.Account;
import com.buddybank.mysuperbank.model.Customer;
import com.buddybank.mysuperbank.service.AccountService;
import com.buddybank.mysuperbank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class AccountController {

    private final AccountService accountService;
    private final CustomerService customerService;

    @GetMapping("/transfer/")
    public String showTransferForm(Model model) {
        model.addAttribute("transferRequest", new TransferRequest());
        return "transfer";
    }

    @PostMapping("/transfer/")
    public String transferMoney(@ModelAttribute TransferRequest transferRequest, RedirectAttributes redirectAttributes) {
        try {
            accountService.transferMoney(transferRequest.getFromAccountId(), transferRequest.getToAccountId(), transferRequest.getAmount(), transferRequest.getDescription());
            redirectAttributes.addFlashAttribute("successMessage", "Transfer successful!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Transfer failed: " + e.getMessage());
        }
        return "redirect:/customers/";
    }

    @Autowired
    public AccountController(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @GetMapping("/accounts/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return "redirect:/customers/";
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

    @GetMapping("/accounts/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Account account = accountService.getAccountById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid account Id:" + id));
        model.addAttribute("account", account);
        return "editAccount";
    }

    @PostMapping("/accounts/update/{id}")
    public String updateAccount(@PathVariable("id") Long id, @ModelAttribute("account") Account account, Model model) {
        account.setId(id);
        accountService.updateAccount(id, account);
        return "redirect:/customers/";
    }
}