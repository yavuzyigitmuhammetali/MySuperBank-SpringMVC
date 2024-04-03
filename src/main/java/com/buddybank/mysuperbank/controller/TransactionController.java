package com.buddybank.mysuperbank.controller;

import com.buddybank.mysuperbank.model.Transaction;
import com.buddybank.mysuperbank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/getall")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}
