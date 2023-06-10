package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.service.impl;

import com.arquitectura.monolitico.api.app.domain.entity.Photo;
import com.arquitectura.monolitico.api.app.domain.exceptions.photo.PhotNotFoundIdentificationException;
import com.arquitectura.monolitico.api.app.domain.exceptions.photo.PhotoNotFoundIdException;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.dbo.PhotoEntity;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.mapper.PhotoEntityMapper;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.repository.SpringDataPhotoRepository;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.service.PhotoDboService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PhotoDboServiceImpl implements PhotoDboService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoDboServiceImpl.class);

    private final SpringDataPhotoRepository photoRepository;

    private final PhotoEntityMapper photoEntityMapper;

    @Override
    public Optional<Photo> save(Photo photo) {
        return Optional.ofNullable(this.photoEntityMapper.toDomain(this.photoRepository.save(this.photoEntityMapper.toDbo(photo))));
    }

    @Override
    public Optional<Photo> findByIdClient(Long idClient) {
        Optional<PhotoEntity> photo=this.photoRepository.findByIdClient(idClient);
        LOGGER.warn("Trata de buscar una foto por el id del cliente");
        LOGGER.warn(null, photo.orElseThrow());
        return Optional.ofNullable(this.photoEntityMapper.toDomain(this.photoRepository.findByIdClient(idClient).orElseThrow()));
    }

    @Override
    public void delete(Photo photo) {
        this.photoRepository.delete(this.photoEntityMapper.toDbo(photo));
    }

    @Override
    public Optional<List<Photo>> listAllPhotos() {
        return Optional.ofNullable(this.photoEntityMapper.toDomain(this.photoRepository.findAll()));
    }

    @Override
    public Optional<Photo> findByIdClientNotFound(Long idClient) {
        return Optional.ofNullable(this.photoEntityMapper.toDomain(this.photoRepository.findByIdClient(idClient).orElseThrow(()-> new PhotNotFoundIdentificationException(idClient.toString()))));
    }

    @Override
    public Optional<Photo> findByIdNotFound(String id) {
        return Optional.ofNullable(this.photoEntityMapper.toDomain(this.photoRepository.findById(id).orElseThrow(()-> new PhotoNotFoundIdException(id))));
    }

}
