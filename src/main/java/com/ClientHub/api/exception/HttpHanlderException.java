package com.ClientHub.api.exception;

import com.ClientHub.api.dto.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class HttpHanlderException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidation(MethodArgumentNotValidException ex) {

        FieldError error = ex.getFieldError();

        ResponseError errorRespone = new ResponseError(HttpStatus.BAD_REQUEST.value(),String.format("El campo %s %s", error.getField(), error.getDefaultMessage()), LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRespone);
    }



    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseError> handlerMethodNotNotAllowed(HttpRequestMethodNotSupportedException ex){

        String message = String.format("%s, Allowed: %s ", ex.getMessage(), ex.getSupportedHttpMethods());

        ResponseError error = new ResponseError(HttpStatus.METHOD_NOT_ALLOWED.value(),message, LocalDateTime.now() );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }



    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseError> handlerMessageNotReadable(HttpMessageNotReadableException ex){

        ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.value(), ex.getMostSpecificCause().getMessage(),LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    } 
}
