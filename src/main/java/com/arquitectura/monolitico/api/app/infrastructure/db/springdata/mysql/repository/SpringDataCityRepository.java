package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.repository;


import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.dbo.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCityRepository extends JpaRepository<CityEntity,Long> {


}

