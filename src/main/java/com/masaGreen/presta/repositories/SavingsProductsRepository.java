package com.masaGreen.presta.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masaGreen.presta.models.SavingsProduct;

public interface SavingsProductsRepository extends JpaRepository<SavingsProduct, Long>{

    Object deleteBySavingsType(String savingsType);

    Optional<SavingsProduct> findBySavingsType(String savingsType);

    
}
