package com.masaGreen.presta.dtos.customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerUpdateDto {
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid phone number")
    @NotBlank(message = "phone can't be blank")
    private String phoneNumber;

    @Email(message = "invalid email")
    private String email;


}
