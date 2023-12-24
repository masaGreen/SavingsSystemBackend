package com.masaGreen.presta.dtos.appUser;

import jakarta.validation.constraints.NotEmpty;

public record PinEditDTO(
        @NotEmpty String oldPin,
        @NotEmpty String newPin

) {

}