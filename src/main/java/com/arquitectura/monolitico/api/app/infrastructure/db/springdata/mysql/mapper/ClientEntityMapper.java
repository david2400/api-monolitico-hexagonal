package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.mapper;



import com.arquitectura.monolitico.api.app.domain.entity.Client;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.dbo.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientEntityMapper {

    ClientEntityMapper INSTANCE = Mappers.getMapper(ClientEntityMapper.class);

    Client toDomain(ClientEntity clientEntity);

    ClientEntity toDbo (Client client);

    List<Client> toDomain(List<ClientEntity> clientEntities);


}
