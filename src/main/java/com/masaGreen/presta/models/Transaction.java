package com.masaGreen.presta.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "Member_number")
    private Long memberNumber;

    @Column(name = "Amount")
    private BigDecimal amount;

    @Column (name = "Current_balance")
    private BigDecimal currentAmount ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Date")
    private Date date;

    @Column(name="Payment_method")
    private String methodOfPayment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Savings_product_id")
    private SavingsProduct savingsProduct;




}

