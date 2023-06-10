package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.repository;

import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.dbo.PhotoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataPhotoRepository extends MongoRepository<PhotoEntity,String> {

    @Query(value = "{'idClient':?0}")
    Optional<PhotoEntity> findByIdClient(Long idClient);
    
}
