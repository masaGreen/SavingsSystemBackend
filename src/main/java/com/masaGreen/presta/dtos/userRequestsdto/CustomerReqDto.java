package com.masaGreen.presta.dtos.userRequestsdto;





import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerReqDto {

    @NotBlank(message = "name can't be blank")
    @Size(max=40, message = "Name too long")
    private String firstName;

    @NotBlank(message = "name can't be blank")
    @Size(max=40, message = "Name too long")
    private String lastName;

     @NotBlank(message = "id can't be blank")
    @Size(max=12, message="Id number invalid")
    private String idNumber;

    @Pattern(regexp = "^0\\d{9}$", message = "Invalid phone number")
     @NotBlank(message = "phone can't be blank")
    private String phoneNumber;
    
    @Email(message = "invalid email")
    private String email;

    @NotNull(message = "memberNumber must be provided")
    private long memberNumber;
}
