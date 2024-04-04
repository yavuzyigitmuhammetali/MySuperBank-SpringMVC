package com.buddybank.mysuperbank.controller;

import com.buddybank.mysuperbank.model.Transaction;
import com.buddybank.mysuperbank.service.AccountService;
import com.buddybank.mysuperbank.service.CustomerService;
import com.buddybank.mysuperbank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("/getall")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public String viewAccountTransactions(@PathVariable Long accountId, Model model) {
        List<Transaction> transactions = accountService.getTransactionsForAccount(accountId);
        model.addAttribute("transactions", transactions);
        return "transactions"; // Name of the HTML file to return (without .html extension)
    }
}
