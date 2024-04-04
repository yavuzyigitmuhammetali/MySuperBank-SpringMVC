package com.buddybank.mysuperbank.service;

import com.buddybank.mysuperbank.model.Account;
import com.buddybank.mysuperbank.model.Customer;
import com.buddybank.mysuperbank.model.Transaction;
import com.buddybank.mysuperbank.repository.AccountRepository;
import com.buddybank.mysuperbank.repository.CustomerRepository;
import com.buddybank.mysuperbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setName(customerDetails.getName());
        customer.setSurname(customerDetails.getSurname());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        customer.setAddress(customerDetails.getAddress());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Account createAccountForCustomer(Long customerId, Account account) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    public Account updateAccount(Long accountId, Account accountDetails) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setAccountType(accountDetails.getAccountType());
        account.setBalance(accountDetails.getBalance());
        return accountRepository.save(account);
    }

    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public Transaction createTransactionForAccount(Long accountId, Transaction transaction) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }
}