package com.masaGreen.presta.services;

import com.masaGreen.presta.ExceptionsHandling.exceptions.InsufficientFundsException;
import com.masaGreen.presta.ExceptionsHandling.exceptions.WrongPinException;
import com.masaGreen.presta.dtos.transactions.CreateTransactionDTO;
import com.masaGreen.presta.models.entities.Account;
import com.masaGreen.presta.models.entities.AppUser;
import com.masaGreen.presta.models.entities.Transaction;
import com.masaGreen.presta.models.enums.TransactionMedium;
import com.masaGreen.presta.models.enums.TransactionType;
import com.masaGreen.presta.repositories.AccountRepository;
import com.masaGreen.presta.repositories.TransactionsRepository;
import com.masaGreen.presta.security.jwt.JwtFilter;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    // private final JwtFilter jwtFilter;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createTransaction(CreateTransactionDTO createTransactionDTO) {
        // find account ,not found throw an error
        Account account = accountService.findByAccountNumber(createTransactionDTO.accountNumber());
        //confirm AppUsers pin
        String pinFromDTO = createTransactionDTO.pin() + account.getAppUser().getPinEncryption();
        if(!passwordEncoder.matches(pinFromDTO,account.getAppUser().getPin())){
            throw new WrongPinException("wrong pin");
        }

        Transaction transaction = new Transaction();

        BigDecimal amount = new BigDecimal(createTransactionDTO.amount());

        if (createTransactionDTO.transactionType().equals(TransactionType.DEPOSIT.getDesc())) {

            transaction.setTransactionType(TransactionType.DEPOSIT);
            
            //update account balance
            account.setBalance(
                    (account.getBalance().add(amount))
                    .subtract(new BigDecimal(createTransactionDTO.transactionCharge())));

        }
        if (createTransactionDTO.transactionType().equals(TransactionType.WITHDRAWAL.getDesc())) {
            // check if the current amount is sufficient to satisfy the withdrawal

            if (account.getBalance().compareTo(amount.add(new BigDecimal(100).add(new BigDecimal(createTransactionDTO.transactionCharge())))) >= 0) {
                
                throw new InsufficientFundsException("account balance is insufficient");
            } else {
                transaction.setTransactionType(TransactionType.WITHDRAWAL);
                //update account balance
                account.setBalance(
                        (account.getBalance().subtract(amount))
                        .subtract(new BigDecimal(createTransactionDTO.transactionCharge())));
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
    /*
     *id logged in can only access its transactions and not for another person unless its staff 
     */
    public List<Transaction> getAllTransactionsByAppUser(String idNumber, HttpServletRequest request) {
        
        String loggedIdNumber = (String)request.getAttribute("idNumber");
        if(
            !loggedIdNumber.equals(idNumber) && SecurityContextHolder.getContext().getAuthentication().getAuthorities().size() == 1
            ) throw new AccessDeniedException("operation denied");
            
        return transactionsRepository.findAllTransactionsByAppUserIdNumber(idNumber);
    }
    public List<Transaction> getAllTRansactionsByAccountNumber(String accountNumber, HttpServletRequest request){
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().size() == 2){
            return transactionsRepository.findAllTransactionsByAccountNumber(accountNumber);
        }else{
            String loggedIdNumber = (String)request.getAttribute("idNumber");
            Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()-> new EntityNotFoundException("account not found"));
            if(!account.getAppUser().getIdNumber().equals(loggedIdNumber)) throw new AccessDeniedException("operation not allowed");
            return transactionsRepository.findAllTransactionsByAccountNumber(accountNumber);
        }
         

        
    }

}
