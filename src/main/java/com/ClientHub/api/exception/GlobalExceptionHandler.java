package com.ClientHub.api.exception;

import com.ClientHub.api.dto.response.ResponseError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> entityNotFound(EntityNotFoundException ex){

        ResponseError error = new ResponseError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }



    @ExceptionHandler(UnchangedValueException.class)
    public ResponseEntity<ResponseError> UnchangedValueException(UnchangedValueException ex){

        ResponseError error = new ResponseError(HttpStatus.CONFLICT.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }





    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseError> handlerNullPointerException(NullPointerException ex){

        ResponseError error = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> handlerIlegalArgument(IllegalArgumentException ex){

        ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
