package com.arquitectura.monolitico.api.app.infrastructure.rest.spring.mapper;


import com.arquitectura.monolitico.api.app.domain.entity.City;
import com.arquitectura.monolitico.api.app.infrastructure.rest.spring.dto.CityDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    City toDomain(CityDto cityDto);

    CityDto toDto (City city);

}
