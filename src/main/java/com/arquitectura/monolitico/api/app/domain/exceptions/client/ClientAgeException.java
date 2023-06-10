package com.arquitectura.monolitico.api.app.domain.exceptions.client;

public class ClientAgeException extends RuntimeException{

    public ClientAgeException() {
        super(String.format("the client must be of legal age"));
    }
}
