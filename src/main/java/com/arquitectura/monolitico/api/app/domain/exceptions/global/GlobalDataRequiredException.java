package com.arquitectura.monolitico.api.app.domain.exceptions.global;

public class GlobalDataRequiredException extends  RuntimeException{

    public GlobalDataRequiredException (){
        super(String.format("enter all the required data"));
    }




}
