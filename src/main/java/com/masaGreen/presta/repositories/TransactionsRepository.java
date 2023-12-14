package com.masaGreen.presta.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.masaGreen.presta.models.entities.Transaction;



public interface TransactionsRepository extends JpaRepository<Transaction, String>{
    @Query("""
            SELECT t FROM Transaction t
            JOIN t.account a
            JOIN a.customer c
            WHERE c.idNumber = :idNumber
                        
            """)
    List<Transaction> findAllTransactionsByCustomerIdNumber(@Param(value = "idNumber") String idNumber);
    
    @Query("""
            SELECT t FROM Transaction t
            JOIN t.account a
            WHERE a.accountNumber  = :accountNumber
            """)
    List<Transaction> findAllTransactionsByAccountNumber(@Param(value="accountNumber") String accountNumber);
}
