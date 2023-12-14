package com.masaGreen.presta.ExceptionsHandling;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

public record ExceptionObject(int httpCode, Map<String,String> errors, Date timeStamp) {

    public static ExceptionObject singleMessageException(int httpCode, String message, Date timeStamp){

        return new ExceptionObject(httpCode, Collections.singletonMap("message",message),timeStamp);
    }
    public static ExceptionObject manyMessagesException(int httpCode, Map<String,String> errors, Date timeStamp){

        return new ExceptionObject(httpCode, errors,timeStamp);
    }
}
