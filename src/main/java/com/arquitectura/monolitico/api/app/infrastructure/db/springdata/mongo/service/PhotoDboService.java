package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.service;

import com.arquitectura.monolitico.api.app.domain.entity.Photo;

import java.util.List;
import java.util.Optional;

public interface PhotoDboService {

    Optional<List<Photo>> listAllPhotos ();

    Optional<Photo> save (Photo photo);

    Optional<Photo> findByIdClient(Long idClient);

    Optional<Photo> findByIdClientNotFound(Long idClient);

    Optional<Photo> findByIdNotFound(String id);

    void delete (Photo photo);
    
}
