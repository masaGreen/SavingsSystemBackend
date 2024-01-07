package com.masaGreen.presta.dtos.accountType;

import com.masaGreen.presta.annotations.NotProfane;

public record EditAccountType(
        @NotProfane
        String name,
        double interest) {

}
