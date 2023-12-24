package com.masaGreen.presta.dtos.appUser;


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
public class AppUserUpdateDto {
    @Pattern(regexp = "^0\\d{9}$", message = "Invalid phone number")
    @NotBlank(message = "phone can't be blank")
    private String phoneNumber;

    @Email(message = "invalid email")
    private String email;


}
