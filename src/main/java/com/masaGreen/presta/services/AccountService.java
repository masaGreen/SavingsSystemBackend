package com.masaGreen.presta.services;

import com.masaGreen.presta.ExceptionsHandling.exceptions.WrongPinException;
import com.masaGreen.presta.dtos.account.AccountCreationResDTO;
import com.masaGreen.presta.dtos.account.CreateAccountDTO;
import com.masaGreen.presta.dtos.transactions.BalanceDTO;
import com.masaGreen.presta.dtos.transactions.BalanceInquiryDTO;
import com.masaGreen.presta.models.entities.Account;
import com.masaGreen.presta.models.entities.AccountType;
import com.masaGreen.presta.models.entities.AppUser;
import com.masaGreen.presta.models.entities.Transaction;
import com.masaGreen.presta.models.enums.TransactionMedium;
import com.masaGreen.presta.models.enums.TransactionType;
import com.masaGreen.presta.repositories.AccountRepository;
import com.masaGreen.presta.repositories.TransactionsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AppUserService appUserService;
    private final AccountTypeService accountTypeService;
    private final TransactionsRepository transactionsRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public AccountCreationResDTO saveAccount(CreateAccountDTO createAccountDTO) {


        AppUser appUser = appUserService.findAppUserById(createAccountDTO.appUserId());
        AccountType accountType = accountTypeService.findByAccountTypeByName(createAccountDTO.accountType());

        String accountNumber = generateAccountNumber(createAccountDTO.branchCode());

        Account account = new Account();
        account.setAccountType(accountType);
        /*
          must deposit the min balance for their account type
         */
        account.setBalance(accountType.getMinimumBalance());
        account.setAppUser(appUser);
        account.setAccountNumber(accountNumber);
        Account createdAccount = accountRepository.save(account);


        /*
         * update user accounts
         */
        var userAccounts = appUser.getAccounts();
        userAccounts.add(createdAccount);
        appUser.setAccounts(userAccounts);
        appUserService.save(appUser);
        /*
         * update the first transaction by that account as a deposit
         */
        Transaction transaction = new Transaction();
        transaction.setAccount(createdAccount);
        transaction.setAmountTransacted(accountType.getMinimumBalance());
        transaction.setTransactionMedium(TransactionMedium.WALK_IN);
        transaction.setTransactionType(TransactionType.DEPOSIT);

        transactionsRepository.save(transaction);

        return new AccountCreationResDTO(createdAccount.getId(), createdAccount.getAccountNumber(),
                createdAccount.getAccountType());

    }

    @Transactional
    public BalanceDTO getAppUserAccountBalance(BalanceInquiryDTO balanceInquiryDTO) {


        AppUser appUser = appUserService.findAppUserById(balanceInquiryDTO.appUserId());

        if (!passwordEncoder.matches(
                (balanceInquiryDTO.pin() + appUser.getPinEncryption()), appUser.getPin())) {
            throw new WrongPinException("invalid pin");
        }
        /*
         * find the specific account of the customer and update its
         * balance due to charges before sending the balance
         */

        Set<Account> accounts = appUser.getAccounts();
        if (accounts.isEmpty())
            throw new EntityNotFoundException("no account found");

        var account = accounts.stream()
                .filter(a -> a.getAccountNumber().equals(balanceInquiryDTO.accountNumber())).toList().get(0);
        account.setBalance(account.getBalance().subtract(new BigDecimal(balanceInquiryDTO.transactionCharge())));
        Account afterBalanceEnquiryNewAccount = accountRepository.save(account);
        // save the transaction

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionCharge(Double.parseDouble(balanceInquiryDTO.transactionCharge()));
        transaction.setTransactionType(TransactionType.BALANCE_CHECK);

        transactionsRepository.save(transaction);

        return new BalanceDTO(afterBalanceEnquiryNewAccount.getBalance());
    }


    public Account findByAccountNumber(String accountNumber) {

        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("account not found"));

    }

    public BalanceDTO getAllAppUsersTotalSavings() {

        return new BalanceDTO(accountRepository.findTotalAppUserSavings());

    }

    /**
     * Account number is a combination of the branch where account is being created from
     * and a random int to make nine digits, can always be changed
     */
    private String generateAccountNumber(String branchCode) {
        int randomInt = new Random().nextInt();

        return String.format("%s%09d", branchCode, Math.abs(randomInt));
    }
}
