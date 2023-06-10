package com.arquitectura.monolitico.api.app.domain.exceptions.photo;

public class PhotoNotFoundIdException extends  RuntimeException{

    public PhotoNotFoundIdException(String id) {
        super(String.format("the photo with id : %s was not found", id));
    }


}
