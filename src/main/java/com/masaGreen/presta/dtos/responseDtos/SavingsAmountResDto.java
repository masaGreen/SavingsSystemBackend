package com.masaGreen.presta.dtos.responseDtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingsAmountResDto {
    private BigDecimal totalSavings;
}
