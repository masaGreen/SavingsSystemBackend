package com.masaGreen.presta.ExceptionsHandling;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.masaGreen.presta.ExceptionsHandling.exceptions.UnverifiedUserException;
import com.masaGreen.presta.ExceptionsHandling.exceptions.WrongPinException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalSecurityExceptionsHandler {
    @ExceptionHandler(UnverifiedUserException.class)
    public ResponseEntity<ExceptionObject> handleUnverifiedUserException(UnverifiedUserException ex) {
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(), ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.UNAUTHORIZED);
    }
     @ExceptionHandler(WrongPinException.class)
    public ResponseEntity<ExceptionObject> handleWrongPinException(WrongPinException ex){
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionObject> handleAccessDeniedException(AccessDeniedException ex) {
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.FORBIDDEN.value(),
                ex.getMessage(), ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
    }
     @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ExceptionObject> handleMalformedJwtException(MalformedJwtException ex) {
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.FORBIDDEN.value(),
                ex.getMessage(), ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionObject> handleSignatureException(SignatureException ex) {
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(), ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.UNAUTHORIZED);
    }
     @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ExceptionObject> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(), ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.UNAUTHORIZED);
    }
     @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionObject> handleExpiredJwtException(ExpiredJwtException ex) {
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(), ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
    }
     @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionObject> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(), ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
    }
}
