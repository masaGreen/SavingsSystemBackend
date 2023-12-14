package com.masaGreen.presta.services;

import com.masaGreen.presta.ExceptionsHandling.exceptions.WrongPinException;
import com.masaGreen.presta.dtos.account.AccountCreationResDTO;
import com.masaGreen.presta.dtos.account.CreateAccountDTO;
import com.masaGreen.presta.dtos.transactions.BalanceDTO;
import com.masaGreen.presta.dtos.transactions.BalanceInquiryDTO;
import com.masaGreen.presta.models.entities.Account;
import com.masaGreen.presta.models.entities.AccountType;
import com.masaGreen.presta.models.entities.Customer;
import com.masaGreen.presta.models.entities.Transaction;
import com.masaGreen.presta.models.enums.TransactionMedium;
import com.masaGreen.presta.models.enums.TransactionType;
import com.masaGreen.presta.repositories.AccountRepository;
import com.masaGreen.presta.repositories.TransactionsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountTypeService accountTypeService;
    private final TransactionsRepository transactionsRepository;


    @Transactional

    public AccountCreationResDTO saveAccount(CreateAccountDTO createAccountDTO) {
        //check if customer exists or throw an exception
        Customer customer = customerService.findCustomerById(createAccountDTO.customerId());
        //check if customer already has that accountType or throw an exception
        AccountType accountType = accountTypeService.findByAccountTypeByName(createAccountDTO.accountType());


        String accountNumber = generateAccountNumber(createAccountDTO.branchCode());

        Account account = new Account();
        account.setAccountType(accountType);
        // must deposit the min balance for their account type
        account.setBalance(accountType.getMinimumBalance());
        account.setCustomer(customer);
        account.setAccountNumber(accountNumber);
        Account createdAccount = accountRepository.save(account);
        //update the first transaction by that account as a deposit
        Transaction transaction = new Transaction();
        transaction.setAccount(createdAccount);
        transaction.setAmountTransacted(accountType.getMinimumBalance());
        transaction.setTransactionMedium(TransactionMedium.WALK_IN);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        
        transactionsRepository.save(transaction);


        return new AccountCreationResDTO(createdAccount.getId(), createdAccount.getAccountNumber(), createdAccount.getAccountType());

    }


    public BalanceDTO getCustomerAccountBalance(BalanceInquiryDTO balanceInquiryDTO) {
        //decrypt the pin from how the client encrypted it using a secret key
        //check if the customer exists or throw an exception
        Customer customer = customerService.findCustomerById(balanceInquiryDTO.customerId());
        //confirm pin is right
        if (!customer.getPin().equals(balanceInquiryDTO.pin() + customer.getPinEncryption())) {
            throw new WrongPinException("invalid pin");
        }

        Set<Account> accounts = customer.getAccounts();
        if (accounts.isEmpty()) throw new EntityNotFoundException("no account found");

        var account = accounts.stream()
                .filter(a -> a.getAccountNumber().equals(balanceInquiryDTO.accountNumber())).toList().get(0);
        //save the transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionCharge(Double.parseDouble(balanceInquiryDTO.transactionCharge()));
        transaction.setTransactionType(TransactionType.BALANCE_CHECK);

        transactionsRepository.save(transaction);

        return new BalanceDTO(account.getBalance());
    }

    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException("account not found"));

    }

    public BalanceDTO getAllCustomersTotalSavings() {

        return new BalanceDTO(accountRepository.findTotalCustomerSavings());


    }

    private String generateAccountNumber(String branchCode) {
        int randomInt = new Random().nextInt();

        return String.format("%s%09d", branchCode, Math.abs(randomInt));
    }
}
