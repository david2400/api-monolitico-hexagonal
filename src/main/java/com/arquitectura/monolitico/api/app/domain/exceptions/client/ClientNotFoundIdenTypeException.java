package com.arquitectura.monolitico.api.app.domain.exceptions.client;

public class ClientNotFoundIdenTypeException extends  RuntimeException{

    public ClientNotFoundIdenTypeException(String identification,Long identificationType) {
        super(String.format("client with identification number: %s and identification type number: %s not found.", identification,identificationType));
    }


}
