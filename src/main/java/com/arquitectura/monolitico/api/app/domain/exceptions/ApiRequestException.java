package com.arquitectura.monolitico.api.app.domain.exceptions;

public class ApiRequestException extends RuntimeException{

    public ApiRequestException(String message) {

        super(message);
    }
}