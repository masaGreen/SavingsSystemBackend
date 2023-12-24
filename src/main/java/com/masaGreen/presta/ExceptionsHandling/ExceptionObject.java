package com.masaGreen.presta.ExceptionsHandling;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public record ExceptionObject(int httpCode, Map<String,String> errors, ZonedDateTime timeStamp) {

    public static ExceptionObject singleMessageException(int httpCode, String message, ZonedDateTime timeStamp){

        return new ExceptionObject(httpCode, Collections.singletonMap("message",message),timeStamp);
    }
    public static ExceptionObject manyMessagesException(int httpCode, Map<String,String> errors, ZonedDateTime timeStamp){

        return new ExceptionObject(httpCode, errors,timeStamp);
    }
}
