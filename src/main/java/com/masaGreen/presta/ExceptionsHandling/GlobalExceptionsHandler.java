package com.masaGreen.presta.ExceptionsHandling;

import com.masaGreen.presta.ExceptionsHandling.exceptions.InsufficientFundsException;
import com.masaGreen.presta.ExceptionsHandling.exceptions.UnverifiedUserException;
import com.masaGreen.presta.ExceptionsHandling.exceptions.WrongPinException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionsHandler {
    
  
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionObject> handleMethodValidationErrors(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors()
            .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        ExceptionObject exceptionObject = ExceptionObject.manyMessagesException(
                HttpStatus.BAD_REQUEST.value(),errors, ZonedDateTime.now());
        log.error("method argument not valid");
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);

    }
      
    

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionObject> handleUsernameNotFoundException(UsernameNotFoundException ex){
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ExceptionObject> handleWrongPinException(InsufficientFundsException ex){
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionObject> handleIllegalArgumentException(IllegalArgumentException ex){
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ExceptionObject> handleEntityExistsException(EntityExistsException ex){
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionObject> handleEntityNotFoundException(EntityNotFoundException ex){
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }
     @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ExceptionObject> handleConnectException(ConnectException ex){
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

      @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionObject> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        ExceptionObject exceptionObject = ExceptionObject.singleMessageException(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),ZonedDateTime.now());
        log.error("exception {}", ex.getMessage());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    
   
}
