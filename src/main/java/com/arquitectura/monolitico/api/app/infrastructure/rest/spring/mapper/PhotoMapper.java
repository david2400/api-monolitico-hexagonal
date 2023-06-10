package com.arquitectura.monolitico.api.app.infrastructure.rest.spring.mapper;

import com.arquitectura.monolitico.api.app.domain.entity.Photo;
import com.arquitectura.monolitico.api.app.infrastructure.rest.spring.dto.PhotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);

    Photo toDomain(PhotoDto clientDto);

    PhotoDto toDto (Photo photo);

    List<PhotoDto> toDto (List<Photo> clients);

}
