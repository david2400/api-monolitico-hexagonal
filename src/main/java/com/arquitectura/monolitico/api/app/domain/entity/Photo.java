package com.arquitectura.monolitico.api.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Photo {


    private String id;


    private Long idClient;


    private String base64;



}
