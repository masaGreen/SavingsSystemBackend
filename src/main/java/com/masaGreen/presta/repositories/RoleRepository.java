package com.masaGreen.presta.repositories;

import com.masaGreen.presta.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    boolean existsByName(String string);
}
