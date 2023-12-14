package com.masaGreen.presta.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masaGreen.presta.models.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer,String>{

    boolean existsByIdNumber(String idNumber);

    Optional<Customer> findByIdNumber(String idNumber);

}
