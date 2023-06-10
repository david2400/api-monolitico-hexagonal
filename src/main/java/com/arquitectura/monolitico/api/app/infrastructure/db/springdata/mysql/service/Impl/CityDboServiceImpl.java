package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service.impl;

import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.repository.SpringDataClientRepository;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service.CityDboService;
import com.arquitectura.monolitico.api.app.domain.exceptions.city.CityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CityDboServiceImpl  implements CityDboService {


    private final SpringDataClientRepository cityRepository;


    @Override
    public void findByIdEmpty(Long id) {
        if(this.cityRepository.findById(id).isEmpty()){
            throw new CityNotFoundException(id);
        }
    }
}
