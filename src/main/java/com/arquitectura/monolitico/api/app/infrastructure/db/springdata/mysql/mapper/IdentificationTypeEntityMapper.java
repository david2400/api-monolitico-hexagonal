package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.mapper;


import com.arquitectura.monolitico.api.app.domain.entity.IdentificationType;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.dbo.IdentificationTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IdentificationTypeEntityMapper {

    IdentificationTypeEntityMapper INSTANCE = Mappers.getMapper(IdentificationTypeEntityMapper.class);

    IdentificationType toDomain(IdentificationTypeEntity identificationTypeEntity);

    IdentificationTypeEntity toDbo (IdentificationType identificationType);



}
