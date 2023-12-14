package com.masaGreen.presta.models.entities;

import com.masaGreen.presta.models.superClasess.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Customer extends BaseEntity{
    
  
    private String firstName;

   
    private String lastName;
  
    @Column(unique = true)
    private String idNumber;
    
    private String phoneNumber;
   
    private String email;

    private String pin;

    private String pinEncryption;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();

}
