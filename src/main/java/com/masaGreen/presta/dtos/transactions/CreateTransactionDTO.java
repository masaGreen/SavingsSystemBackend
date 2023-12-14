package com.masaGreen.presta.dtos.transactions;

import jakarta.validation.constraints.NotEmpty;

public record CreateTransactionDTO(

        @NotEmpty(message = "pi must be provided")
        String pin,
        @NotEmpty(message = "pi must be provided")
        String accountNumber,
        String amount,

        String transactionType,
        String transactionMedium,
        String transactionCharge

) {

}
