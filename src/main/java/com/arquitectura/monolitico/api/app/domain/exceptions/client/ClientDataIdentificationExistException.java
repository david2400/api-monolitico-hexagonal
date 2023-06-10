package com.arquitectura.monolitico.api.app.domain.exceptions.client;

public class ClientDataIdentificationExistException extends RuntimeException{

    public ClientDataIdentificationExistException(String identification) {
        super(String.format("The identification number : %s is existing", identification));
    }



}
