package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.mapper;

import java.util.List;

import com.arquitectura.monolitico.api.app.domain.entity.Photo;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.dbo.PhotoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface PhotoEntityMapper {

    PhotoEntityMapper INSTANCE = Mappers.getMapper(PhotoEntityMapper.class);

    Photo toDomain(PhotoEntity photoEntity);

    PhotoEntity toDbo (Photo photo);

    List<Photo> toDomain(List<PhotoEntity> listPhoto);

}
