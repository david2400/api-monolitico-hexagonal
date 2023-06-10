package com.arquitectura.monolitico.api.app.infrastructure.rest.spring.mapper;

import com.arquitectura.monolitico.api.app.domain.entity.Client;
import com.arquitectura.monolitico.api.app.infrastructure.rest.spring.dto.ClientRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientRequestMapper {

    ClientRequestMapper INSTANCE = Mappers.getMapper(ClientRequestMapper.class);

     @Mapping(source = "cityId",target = "city.id")
     @Mapping(source = "identificationTypeId",target = "identificationType.id")
    public Client DTOtoEntity(ClientRequestDto clientRequestDTO);


    @Mapping(source = "city.id",target = "cityId")
    @Mapping(source = "identificationType.id",target = "identificationTypeId")
    public ClientRequestDto EntityToDTO(Client client);

}
