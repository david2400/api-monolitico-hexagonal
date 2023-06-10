package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.data;

import com.arquitectura.monolitico.api.app.domain.entity.Photo;

public class PhotoData {

    public static final Photo photoUno = Photo.builder()
            .id("1")
            .base64("c2Rmc2RzYw==")
            .idClient(1l)
            .build();
    public static final Photo photoDos = Photo.builder()
            .id("2")
            .base64("c2Rmc2RzYw==")
            .idClient(2l)
            .build();
    public static final Photo photoTres = Photo.builder()
            .id("3")
            .base64("c2Rmc2RzYw==")
            .idClient(3l)
            .build();

    public static final Photo photoCuatro = Photo.builder()
            .id("4")
            .base64("c2Rmc2RzYw==")
            .idClient(4l)
            .build();



}
