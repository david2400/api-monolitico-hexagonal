package com.arquitectura.monolitico.api.app.infrastructure.rest.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long id;


    private String name;


    private String lastName;


    private String age;


    private String identification;


    private String mail;


    private CityDto city;


    private IdentificationTypeDto identificationType;


}
