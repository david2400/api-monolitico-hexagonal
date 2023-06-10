package com.arquitectura.monolitico.api.app.application.repository;


import com.arquitectura.monolitico.api.app.domain.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoRepository {

    List<Photo> listAllPhotos();

    Photo getPhotoByClientIdentification(String identification) throws IOException;

    Photo getPhotoById(String id) throws  IOException;

    Photo save(MultipartFile multipartFile, String identification) throws IOException;

    String deleteById(String idPhoto) throws  IOException;

    String deleteByIdentification(String identification)throws  IOException;

    Photo update (MultipartFile multipartFile, String identification) throws IOException;






}
