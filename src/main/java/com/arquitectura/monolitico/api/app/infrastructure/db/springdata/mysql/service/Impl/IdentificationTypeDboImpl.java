package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service.impl;

import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.repository.SpringDataIdentificationTypeRepository;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service.IdentificationTypeDboService;
import com.arquitectura.monolitico.api.app.domain.exceptions.IdentificationType.IdentificationTypeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class IdentificationTypeDboImpl implements IdentificationTypeDboService {

    private final SpringDataIdentificationTypeRepository identificationTypeRepository;

    @Override
    public void findByIdEmpty(Long id) {
        if(this.identificationTypeRepository.findById(id).isEmpty()){
            throw new IdentificationTypeNotFoundException(id);
        }
    }
}
