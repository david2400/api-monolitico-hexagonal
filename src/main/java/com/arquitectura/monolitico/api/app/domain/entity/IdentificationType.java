package com.arquitectura.monolitico.api.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentificationType {

    private Long id;


    private String name;


    private String description;


}