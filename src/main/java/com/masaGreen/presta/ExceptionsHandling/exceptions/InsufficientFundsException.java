package com.masaGreen.presta.ExceptionsHandling.exceptions;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message){
        super(message);
    }
    
}
