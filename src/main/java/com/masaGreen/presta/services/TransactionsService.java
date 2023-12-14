package com.masaGreen.presta.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.masaGreen.presta.ExceptionsHandling.exceptions.WrongPinException;
import org.springframework.stereotype.Service;

import com.masaGreen.presta.ExceptionsHandling.exceptions.InsufficientFundsException;
import com.masaGreen.presta.dtos.transactions.CreateTransactionDTO;
import com.masaGreen.presta.models.entities.Account;
import com.masaGreen.presta.models.entities.Transaction;
import com.masaGreen.presta.models.enums.TransactionMedium;
import com.masaGreen.presta.models.enums.TransactionType;
import com.masaGreen.presta.repositories.AccountRepository;
import com.masaGreen.presta.repositories.TransactionsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Transactional
    public String createTransaction(CreateTransactionDTO createTransactionDTO) {
        // find account ,not found throw an error
        Account account = accountService.findByAccountNumber(createTransactionDTO.accountNumber());
        //confirm customers pin
        String actualPin = createTransactionDTO.pin() + account.getCustomer().getPinEncryption();
        if(account.getCustomer().getPin().equals(actualPin)){
            throw new WrongPinException("wrong pin");
        }

        Transaction transaction = new Transaction();

        BigDecimal amount = new BigDecimal(createTransactionDTO.amount());

        if (createTransactionDTO.transactionType().equals(TransactionType.DEPOSIT.getDesc())) {

            transaction.setTransactionType(TransactionType.DEPOSIT);
            
            //update account balance
            account.setBalance(
                    (account.getBalance().add(amount)).subtract(new BigDecimal(createTransactionDTO.transactionCharge())));

        }
        if (createTransactionDTO.transactionType().equals(TransactionType.WITHDRAWAL.getDesc())) {
            // check if the current amount is sufficient to satisfy the withdrawal

            if (account.getBalance().compareTo(
                    amount.add(new BigDecimal(100).add(new BigDecimal(createTransactionDTO.transactionCharge())))) >= 0) {
                throw new InsufficientFundsException("account balance is insufficient");
            } else {
                transaction.setTransactionType(TransactionType.WITHDRAWAL);
                //update account balance
                account.setBalance(
                        (account.getBalance().subtract(amount)).subtract(new BigDecimal(createTransactionDTO.transactionCharge())));
            }

        }

        // save the transaction
        transaction.setAmountTransacted(amount);
        transaction.setAccount(account);
        transaction.setTransactionCharge(Double.parseDouble(createTransactionDTO.transactionCharge()));
        transaction.setTransactionMedium(TransactionMedium.stringToEnum(createTransactionDTO.transactionMedium()));
        Transaction createdTransaction = transactionsRepository.save(transaction);
        // update transactions for the account

        Set<Transaction> transactions = account.getTransactions();
        transactions.add(createdTransaction);
        account.setTransactions(transactions);
        accountRepository.save(account);

               

        return "transaction completed successfully";

    }


    public List<Transaction> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    public List<Transaction> getAllTransactionsByCustomer(String idNumber) {
        return transactionsRepository.findAllTransactionsByCustomerIdNumber(idNumber);
    }
    public List<Transaction> getAllTRansactionsByAccountNumber(String accountNumber){
        return transactionsRepository.findAllTransactionsByAccountNumber(accountNumber);
    }

}
