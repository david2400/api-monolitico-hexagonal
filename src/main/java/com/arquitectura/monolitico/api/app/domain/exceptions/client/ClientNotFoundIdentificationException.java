package com.arquitectura.monolitico.api.app.domain.exceptions.client;

public class ClientNotFoundIdentificationException extends RuntimeException  {

    public ClientNotFoundIdentificationException(String identification) {
        super(String.format("Client with Identification number: %s not found.", identification));
    }



}