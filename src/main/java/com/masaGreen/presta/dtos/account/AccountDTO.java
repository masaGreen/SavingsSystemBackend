package com.masaGreen.presta.dtos.account;

import java.math.BigDecimal;
import java.util.Set;

import com.masaGreen.presta.models.entities.Account;
import com.masaGreen.presta.models.entities.AccountType;
import com.masaGreen.presta.models.entities.Customer;
import com.masaGreen.presta.models.entities.Transaction;

public class AccountDTO
   {
    private String id;
    private String createdAt;
    private String updatedAt;
    private String accountNumber;
    private BigDecimal balance;
    private Customer customer ;
    private AccountType accountType;
    private Set<Transaction> transactions;

    public AccountDTO(Account account){
    this.id = account.getId();
    this.createdAt = account.getCreatedAt().toString();
    this.updatedAt = account.getUpdatedAt().toString();
    this.accountNumber = account.getAccountNumber();
    this.balance = account.getBalance();
    this.customer = account.getCustomer();
    this.accountType = account.getAccountType();
    this.transactions = account.getTransactions();
    }
}
