package com.masaGreen.presta.repositories;


import com.masaGreen.presta.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,String>{

    boolean existsByIdNumber(String idNumber);

    Optional<Customer> findByIdNumber(String idNumber);

}
