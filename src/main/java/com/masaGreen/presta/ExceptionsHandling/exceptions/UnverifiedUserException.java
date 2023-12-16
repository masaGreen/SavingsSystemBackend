package com.masaGreen.presta.ExceptionsHandling.exceptions;

public class UnverifiedUserException extends RuntimeException{
    public UnverifiedUserException(String message){
        super(message);
    }
}
