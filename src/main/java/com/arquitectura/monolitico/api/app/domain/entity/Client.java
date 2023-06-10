package com.arquitectura.monolitico.api.app.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {


    private Long id;


    private String name;


    private String lastName;


    private String age;


    private String identification;


    private String mail;


    private City city;


    private IdentificationType identificationType;


}