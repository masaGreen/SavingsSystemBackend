package com.masaGreen.presta.dtos.userRequestsdto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReqDto {

       @NotNull(message = "memberNumber must be provided")
       private Long memberNumber;

       @NotNull(message = "amount can't e empty")
       private BigDecimal amount;

       @NotBlank(message = "must provide a payment method")
       private String methodOfPayment;

       @Size(max=70, message="name too long")
       @Builder.Default
       private String savingsType="Normal";
}