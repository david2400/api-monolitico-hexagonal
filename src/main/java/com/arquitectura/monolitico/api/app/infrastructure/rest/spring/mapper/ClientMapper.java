package com.arquitectura.monolitico.api.app.infrastructure.rest.spring.mapper;


import com.arquitectura.monolitico.api.app.domain.entity.Client;
import com.arquitectura.monolitico.api.app.infrastructure.rest.spring.dto.ClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client toDomain(ClientDto clientDto);

    ClientDto toDto (Client client);

    List<ClientDto> toDto (List<Client> clients);


}
