package com.masaGreen.presta.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.masaGreen.presta.models.entities.Account;

public interface AccountRepository extends JpaRepository<Account, String>{

    Optional<Account> findByAccountNumber(String accountNumber);

    @Query("SELECT SUM(ac.balance) FROM Account ac")
    BigDecimal findTotalCustomerSavings();
    
}
