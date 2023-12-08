package com.masaGreen.presta.dtos.customer;

public record CreateCustomer(
    String firstName,
    String lastName,
    String idNumber,
    String phoneNumber,
    String email
    
) {
    
}
