package com.ClientHub.api.exception;

import com.ClientHub.api.dto.response.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class PlanHandlerException {

    @ExceptionHandler(PlanNoFoundException.class)
    public ResponseEntity<ResponseError> handlerPlanNoFoundException(PlanNoFoundException ex){

        ResponseError error = new ResponseError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
