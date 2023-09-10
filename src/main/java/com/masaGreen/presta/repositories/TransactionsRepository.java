package com.masaGreen.presta.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.masaGreen.presta.models.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findAllByMemberNumber(Long memberNumber);
    
}
