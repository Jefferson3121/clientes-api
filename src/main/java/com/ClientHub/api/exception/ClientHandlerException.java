package com.ClientHub.api.exception;

import com.ClientHub.api.dto.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ClientHandlerException {

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ResponseError> checkIfClientExists(ClientAlreadyExistsException ex){

        ResponseError error = new ResponseError(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
