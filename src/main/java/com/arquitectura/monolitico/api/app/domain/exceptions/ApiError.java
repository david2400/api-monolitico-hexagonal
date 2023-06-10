package com.arquitectura.monolitico.api.app.domain.exceptions;

import lombok.Getter;

@Getter
public class ApiError {

    private  String exceptionName;
    private  String message;

    public ApiError(String exceptionName, String message) {
        this.exceptionName = exceptionName;
        this.message = message;
    }

    public ApiError (String message){
        this.message=message;
    }
}