package com.arquitectura.monolitico.api.app.infrastructure.rest.spring.mapper;

import com.arquitectura.monolitico.api.app.domain.entity.Photo;
import com.arquitectura.monolitico.api.app.infrastructure.rest.spring.dto.PhotoRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhotoRequestMapper {

    PhotoRequestMapper INSTANCE = Mappers.getMapper(PhotoRequestMapper.class);

     Photo toDomain(PhotoRequestDTO photoRequestDTO);

     PhotoRequestDTO toDto(Photo photo);

}
