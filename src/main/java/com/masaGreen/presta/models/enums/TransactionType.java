package com.masaGreen.presta.models.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAWAL("withdrawal"),
    BALANCE_CHECK("balance_check");

    private final String desc;

    TransactionType(String description) {
        this.desc = description;
    }
}
