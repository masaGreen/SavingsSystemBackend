package com.masaGreen.presta.models.entities;

import com.masaGreen.presta.models.superClasess.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountType extends BaseEntity {
 
    private String name;
    private Double interest;
    private BigDecimal minimumBalance;
   
}

