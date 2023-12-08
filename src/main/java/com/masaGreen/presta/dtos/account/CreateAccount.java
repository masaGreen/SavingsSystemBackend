package com.masaGreen.presta.dtos.account;

import java.util.List;

public record CreateAccount(
    String customerId,
    String branchCode,
    List<String> accountType
) {
    
}
