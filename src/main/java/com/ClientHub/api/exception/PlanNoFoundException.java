package com.ClientHub.api.exception;

public class PlanNoFoundException extends RuntimeException{
    public PlanNoFoundException(String message){
        super(message);
    }
}
