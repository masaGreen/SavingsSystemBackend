package com.masaGreen.presta.ExceptionsHandling.exceptions;

public class WrongPinException extends RuntimeException {

    public WrongPinException(String message) {
        super(message);
    }
}
