package com.masaGreen.presta.dtos.account;


import com.masaGreen.presta.models.entities.AccountType;

public record AccountCreationResDTO(

        String id,

        String accountNUmber,

        AccountType accountType
) {

}
