package com.masaGreen.presta.dtos.appUser;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AppUserUpdateDto (
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid phone number")
    @NotBlank(message = "phone can't be blank")
    String phoneNumber,

    @Email(message = "invalid email")
    String email
){}

