package com.masaGreen.presta.dtos.transactions;

public record CreateTransaction(

    String accountNumber,
    String amount,
    String transactionType,
    String transactionMedium

) {
    
}
