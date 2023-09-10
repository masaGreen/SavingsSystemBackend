package com.masaGreen.presta.dtos.userRequestsdto;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingsProductReqDto {
    @Size(max=70, message="name too long")
    private  String savingsType;
}
