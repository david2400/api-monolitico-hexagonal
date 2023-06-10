package com.arquitectura.monolitico.api.app.infrastructure.rest.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDto {

    private String id;

    private Long idClient;

    private String base64;


}
