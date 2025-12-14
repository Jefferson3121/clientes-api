package com.ClientHub.api.exception;

public class UnchangedValueException extends RuntimeException{
    public UnchangedValueException(String message){
        super(message);
    }
}
