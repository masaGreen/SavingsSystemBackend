package com.masaGreen.presta.dtos.account;


import com.masaGreen.presta.annotations.NotProfane;
import jakarta.validation.constraints.NotBlank;

public record CreateAccountDTO(
        @NotBlank
        String customerId,
        String branchCode,
        @NotProfane
        String accountType
) {

}

