package com.arquitectura.monolitico.api.app.infrastructure.rest.spring.mapper;


import com.arquitectura.monolitico.api.app.domain.entity.IdentificationType;
import com.arquitectura.monolitico.api.app.infrastructure.rest.spring.dto.IdentificationTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IdentificationTypeMapper {

    IdentificationTypeMapper INSTANCE = Mappers.getMapper(IdentificationTypeMapper.class);

    IdentificationType toDomain(IdentificationTypeDto identificationTypeDto);

    IdentificationTypeDto toDto (IdentificationType identificationType);


}
