package com.masaGreen.presta.dtos.transactions;

import jakarta.validation.constraints.NotEmpty;

public record BalanceInquiryDTO(
        @NotEmpty
        String pin,
        @NotEmpty
        String customerId,
        @NotEmpty
        String accountNumber,
        String transactionCharge) {
    
}
