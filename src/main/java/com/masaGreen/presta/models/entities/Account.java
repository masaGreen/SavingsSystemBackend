package com.masaGreen.presta.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.masaGreen.presta.models.superClasess.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  Account extends BaseEntity {
    
    
    private String accountNumber;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name="account_type_id")
    private AccountType accountType;

    @OneToMany(mappedBy="account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Transaction> transactions = new HashSet<>();
}
