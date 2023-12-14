package com.masaGreen.presta.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masaGreen.presta.models.entities.AccountType;

public interface AccountTypeRepository extends JpaRepository<AccountType, String> {

    Optional<AccountType> findByName(String id);

    boolean existsByName(String name);
    
}
