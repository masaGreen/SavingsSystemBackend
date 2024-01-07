package com.masaGreen.presta.repositories;


import com.masaGreen.presta.models.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

    boolean existsByIdNumber(String idNumber);

    Optional<AppUser> findByIdNumber(String idNumber);

    Optional<AppUser> findByValidationString(String code);

    Optional<AppUser> findByEmail(String email);
}
