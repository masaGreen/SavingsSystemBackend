package com.masaGreen.presta.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingsProduct {
 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Product_id")
    private Long productId;

    @Column(name="savings-type")
    @Builder.Default
    private  String savingsType = "normal";

    @Column(name="Creation_date")
    @Temporal(TemporalType.DATE)
    private Date date;
   
    @Column(name="Balance")
    @Builder.Default
    private BigDecimal balance=new BigDecimal(0.0);

    @OneToMany(mappedBy = "savingsProduct", cascade = CascadeType.ALL)
     @Builder.Default
     @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

}

