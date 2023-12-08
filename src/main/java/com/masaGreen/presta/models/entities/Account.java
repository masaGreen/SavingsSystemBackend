package com.masaGreen.presta.models.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.masaGreen.presta.models.superClasess.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {
    
    
    private String accountNumber;
    private BigDecimal balance;

    
    @ManyToOne
    @JoinColumn(name = "cutomer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name="account_type_id")
    private AccountType accountType;

    @OneToMany(mappedBy="account", cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();
}
