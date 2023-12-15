package com.masaGreen.presta.models.entities;

import com.masaGreen.presta.models.superClasess.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class AppUser extends BaseEntity{
    
  
    private String firstName;

   
    private String lastName;
  
    @Column(unique = true)
    private String idNumber;
    
    private String phoneNumber;
   
    private String email;

    private String pin;

    private String pinEncryption;

    private boolean isVerified;
    private String validationString;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "account-role",
            joinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();

    public Set<Role> addRoles(Set<String> rolesNew){

        roles.addAll(rolesNew.stream().map(Role::new).collect(Collectors.toSet()));
        return roles;
    }

}
