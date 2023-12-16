package com.masaGreen.presta.ExceptionsHandling;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.masaGreen.presta.ExceptionsHandling.exceptions.UnverifiedUserException;

import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class GlobalSecurityExceptionsHandler {
     @ExceptionHandler(UnverifiedUserException.class)
    public ResponseEntity<ExceptionObject> handleUnverifiedUserException(UnverifiedUserException ex){
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),new Date());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.UNAUTHORIZED);
    }
}
