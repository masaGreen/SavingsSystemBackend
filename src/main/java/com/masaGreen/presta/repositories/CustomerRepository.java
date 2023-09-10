package com.masaGreen.presta.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.masaGreen.presta.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

    void deleteByMemberNumber(long memberNumber);

    Optional<Customer> findByMemberNumber(long memberNumber);
    
}
