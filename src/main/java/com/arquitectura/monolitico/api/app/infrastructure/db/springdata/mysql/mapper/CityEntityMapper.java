package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.mapper;


import com.arquitectura.monolitico.api.app.domain.entity.City;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.dbo.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityEntityMapper {

    CityEntityMapper INSTANCE = Mappers.getMapper(CityEntityMapper.class);

    City toDomain(CityEntity cityEntity);

    CityEntity toDbo (City city);

}
