package com.ClientHub.api.exception;

public class ClientAlreadyExistsException extends RuntimeException{
    public ClientAlreadyExistsException(String message){
        super(message);
    }
}
