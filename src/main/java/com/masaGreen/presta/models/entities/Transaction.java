package com.masaGreen.presta.models.entities;

import com.masaGreen.presta.models.enums.TransactionMedium;
import com.masaGreen.presta.models.enums.TransactionType;
import com.masaGreen.presta.models.superClasess.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {
    @Column(name = "Amount")
    private BigDecimal amountTransacted;

    @Enumerated(EnumType.STRING)
    private TransactionMedium transactionMedium;

    private Double transactionCharge;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;


}

