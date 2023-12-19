package com.masaGreen.presta.dtos.appUser;

import com.masaGreen.presta.annotations.NotProfane;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateAppUser(
        @NotProfane
        @NotBlank(message = "name can't be blank")
        @Size(max = 40, message = "Name too long")
        String firstName,
        @NotProfane
        @NotBlank(message = "name can't be blank")
        @Size(max = 40, message = "Name too long")
        String lastName,

        @Pattern(regexp = "^[0-9]+$", message = "id must be in digits")
        @NotBlank(message = "id can't be blank")
        @Size(max = 20, message = "Id number too long")
        String idNumber,
        @NotBlank(message = "can't be blank")
        @Pattern(regexp = "^0\\d{9}$", message = "invalid phone number")
        String phoneNumber,
        @Email
        String email,

        Set<String> roles


) {

}
