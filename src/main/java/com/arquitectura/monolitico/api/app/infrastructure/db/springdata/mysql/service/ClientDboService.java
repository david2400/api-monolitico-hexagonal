package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service;

import com.arquitectura.monolitico.api.app.domain.entity.Client;
import com.arquitectura.monolitico.api.app.domain.entity.IdentificationType;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ClientDboService {

    Optional <List<Client>> findAll();

    Optional<List<Client>> findByIdentificationType(IdentificationType identificationType);

    Optional<Client> findByIdentificationNotFound(String identification);

    Optional<Client> findById (Long id);


    Optional<Client> findByIdNotFound (Long id);

    Optional<Client> findByIdentificationAndIdentificationTypeNotFound(String  identification, IdentificationType identificationType);

    Optional<Client> findByIdentificationAndIdentificationType(String  identification, IdentificationType identificationType);


    void validPresentFindByIdentification(String identification);

    void validPresentFindByMail(String mail) throws IOException;

    Optional<Client> save (Client client);

    void delete (Client client);



}
