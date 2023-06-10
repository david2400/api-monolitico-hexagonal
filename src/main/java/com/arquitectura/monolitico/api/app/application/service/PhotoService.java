package com.arquitectura.monolitico.api.app.application.service;

import com.arquitectura.monolitico.api.app.application.repository.PhotoRepository;
import com.arquitectura.monolitico.api.app.domain.entity.Client;
import com.arquitectura.monolitico.api.app.domain.entity.Photo;
import com.arquitectura.monolitico.api.app.domain.exceptions.global.GlobalDataRequiredException;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.service.PhotoDboService;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service.ClientDboService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class PhotoService implements PhotoRepository {

    @Autowired
    private PhotoDboService photoDboService;

    @Autowired
    private ClientDboService clienteDboService;

    @Override
    public List<Photo> listAllPhotos() {
        return this.photoDboService.listAllPhotos().orElseThrow();
    }

    @Override
    public Photo getPhotoByClientIdentification(String identification) throws IOException {

        if (identification != null) {

            Client clientFound = this.clienteDboService.findByIdentificationNotFound(identification).orElseThrow();
            return this.photoDboService.findByIdClientNotFound(clientFound.getId()).orElseThrow();
        } else {
            throw new GlobalDataRequiredException();
        }

    }

    @Override
    public Photo getPhotoById(String id) throws IOException {
        if (id != null) {
            return this.photoDboService.findByIdNotFound(id).orElseThrow();
        } else {
            throw new GlobalDataRequiredException();
        }
    }

    @Override
    public Photo save(MultipartFile multipartFile, String identification) throws IOException {
        return null;
    }

    @Override
    public String deleteById(String idPhoto) throws IOException {
        return null;
    }

    @Override
    public String deleteByIdentification(String identification) throws IOException {
        return null;
    }

    @Override
    public Photo update(MultipartFile multipartFile, String identification) throws IOException {
        return null;
    }
}
