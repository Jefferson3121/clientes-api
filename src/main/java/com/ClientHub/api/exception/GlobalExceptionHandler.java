package com.ClientHub.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ResponseError> handleClientNotFoundException(ClientNotFoundException ex){

        ResponseError error = new ResponseError(HttpStatus.NOT_FOUND.value(),ex.getMessage(),LocalDateTime.now());
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ResponseError> checkIfClientExists(ClientAlreadyExistsException ex){

        ResponseError error = new ResponseError(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
