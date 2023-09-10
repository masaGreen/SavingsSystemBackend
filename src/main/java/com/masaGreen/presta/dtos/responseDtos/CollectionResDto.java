package com.masaGreen.presta.dtos.responseDtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionResDto<T> {
    private List<?> data; 
}
