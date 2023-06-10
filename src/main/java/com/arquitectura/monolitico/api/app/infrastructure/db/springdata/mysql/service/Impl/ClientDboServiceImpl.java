package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service.impl;

import com.arquitectura.monolitico.api.app.domain.entity.Client;
import com.arquitectura.monolitico.api.app.domain.entity.IdentificationType;
import com.arquitectura.monolitico.api.app.domain.exceptions.client.*;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service.ClientDboService;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.mapper.ClientEntityMapper;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.mapper.IdentificationTypeEntityMapper;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.repository.SpringDataClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * El intermediario entre la base de datos y la logica de negocio
 */

@RequiredArgsConstructor
@Service
public class ClientDboServiceImpl  implements ClientDboService {



    private final SpringDataClientRepository clientRepository;

    private  final ClientEntityMapper clientEntityMapper;

    private  final IdentificationTypeEntityMapper identificationTypeEntityMapper;


    @Override
    public Optional<List<Client>> findAll() {
        return Optional.ofNullable(this.clientEntityMapper.toDomain(this.clientRepository.findAll()));
    }

    @Override
    public Optional<List<Client>> findByIdentificationType(IdentificationType identificationType) {
        return Optional.ofNullable(this.clientEntityMapper.toDomain(this.clientRepository.findByIdentificationType(this.identificationTypeEntityMapper.toDbo(identificationType)).orElseThrow()));
    }

    @Override
    public Optional<Client> findByIdentificationNotFound(String identification) {
        return Optional.ofNullable(this.clientEntityMapper.toDomain(this.clientRepository.findByIdentification(identification).orElseThrow(()->new ClientNotFoundIdentificationException(identification))));
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(this.clientEntityMapper.toDomain(this.clientRepository.findById(id).get()));
    }

    @Override
    public Optional<Client> findByIdNotFound(Long id) {
        return  Optional.ofNullable(this.clientEntityMapper.toDomain(this.clientRepository.findById(id).orElseThrow(()-> new ClientNotFoundIdException(id))));
    }

    @Override
    public Optional<Client> findByIdentificationAndIdentificationTypeNotFound(String identification, IdentificationType identificationType) {
        return Optional.ofNullable(this.clientEntityMapper.toDomain(
                this.clientRepository.findByIdentificationAndIdentificationType(identification,this.identificationTypeEntityMapper.toDbo(identificationType)).orElseThrow(()-> new ClientNotFoundIdenTypeException(identification,identificationType.getId()))));
    }

    @Override
    public Optional<Client> findByIdentificationAndIdentificationType(String identification, IdentificationType identificationType) {
        return Optional.ofNullable(this.clientEntityMapper.toDomain(this.clientRepository.findByIdentificationAndIdentificationType(identification,this.identificationTypeEntityMapper.toDbo(identificationType)).get()));
    }

    @Override
    public void validPresentFindByIdentification(String identification) {

        if(this.clientRepository.findByIdentification(identification).isPresent()){
            throw new ClientDataIdentificationExistException(identification);
        }
    }

    @Override
    public void validPresentFindByMail(String mail) throws IOException {
       if(this.clientRepository.findByMail(mail).isPresent()){
           throw new ClientDataMailExistException(mail);
       }
    }

    @Override
    public Optional<Client> save(Client client) {
        return Optional.ofNullable(this.clientEntityMapper.toDomain(this.clientRepository.save(clientEntityMapper.toDbo(client))));
    }

    @Override
    public void delete(Client client) {
        this.clientRepository.delete(clientEntityMapper.toDbo(client));
    }
}
