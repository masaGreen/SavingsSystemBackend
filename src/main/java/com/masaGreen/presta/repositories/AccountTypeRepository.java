package com.masaGreen.presta.repositories;

import com.masaGreen.presta.models.entities.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, String> {

    Optional<AccountType> findByName(String id);

    boolean existsByName(String name);
    
}
