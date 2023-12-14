package com.masaGreen.presta.models.enums;

import lombok.Getter;

@Getter
public enum TransactionMedium {
   

    WALK_IN("walk_in"),
    MPESA("mpesa"),
    ATM("atm");
    
    private final String medium;
    TransactionMedium(String desc){
        this.medium = desc;
    }

    public static TransactionMedium stringToEnum(String s){
        for(TransactionMedium transactionMedium: TransactionMedium.values()){
            if(transactionMedium.getMedium().equals(s)){
                return transactionMedium;
            }
            
        }
        throw new IllegalArgumentException("transactionmedium not acceptable");
    }
}
