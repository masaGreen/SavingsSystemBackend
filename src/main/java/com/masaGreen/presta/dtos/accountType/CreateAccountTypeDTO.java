package com.masaGreen.presta.dtos.accountType;

import com.masaGreen.presta.annotations.NotProfane;

import java.math.BigDecimal;

public record CreateAccountTypeDTO(
        @NotProfane
        String name,
        double interest,
        BigDecimal minimumBalance

) {

}
