package com.masaGreen.presta.dtos.account;

import com.masaGreen.presta.models.entities.Account;
import com.masaGreen.presta.models.entities.AccountType;
import com.masaGreen.presta.models.entities.AppUser;
import com.masaGreen.presta.models.entities.Transaction;

import java.math.BigDecimal;
import java.util.Set;

public class AccountDTO
   {
    private String id;
    private String createdAt;
    private String updatedAt;
    private String accountNumber;
    private BigDecimal balance;
    private AppUser appUser;
    private AccountType accountType;
    private Set<Transaction> transactions;

    public AccountDTO(Account account){
    this.id = account.getId();
    this.createdAt = account.getCreatedAt().toString();
    this.updatedAt = account.getUpdatedAt().toString();
    this.accountNumber = account.getAccountNumber();
    this.balance = account.getBalance();
    this.appUser = account.getAppUser();
    this.accountType = account.getAccountType();
    this.transactions = account.getTransactions();
    }
}
