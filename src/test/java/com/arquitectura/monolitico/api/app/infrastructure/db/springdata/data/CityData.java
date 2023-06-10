package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.data;

import com.arquitectura.monolitico.api.app.domain.entity.City;

public class CityData {

    public static  final City cityArmenia = City.builder().id(1l).name("armenia").build();
    public static  final City cityPereira = City.builder().id(2l).name("pereira").build();
    public static  final City cityManizales = City.builder().id(3l).name("manizales").build();
    public static  final City cityBogota = City.builder().id(4l).name("bogota").build();



}
