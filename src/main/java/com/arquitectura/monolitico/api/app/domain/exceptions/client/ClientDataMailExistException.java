package com.arquitectura.monolitico.api.app.domain.exceptions.client;

public class ClientDataMailExistException extends RuntimeException{

    public ClientDataMailExistException(String  mail) {
        super(String.format("The mail : %s is existing", mail));
    }


}
