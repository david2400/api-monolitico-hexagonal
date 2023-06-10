package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.repository;


import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.dbo.ClientEntity;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.dbo.IdentificationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataClientRepository extends JpaRepository<ClientEntity,Long> {

    Optional<ClientEntity> findByIdentification(String identification);
    Optional<ClientEntity> findByMail(String mail);
    Optional<ClientEntity> findByIdentificationAndIdentificationType(String  identification, IdentificationTypeEntity identificationType);
    Optional<List<ClientEntity>> findByIdentificationType(IdentificationTypeEntity identificationType);







}
