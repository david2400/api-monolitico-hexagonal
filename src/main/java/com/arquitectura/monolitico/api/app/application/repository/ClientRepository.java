package com.arquitectura.monolitico.api.app.application.repository;


import com.arquitectura.monolitico.api.app.domain.entity.Client;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClientRepository {

    List<Client> listAllClients() ;

    List<Client>  listAllClientsIdentificationType(String idIdentificationType)throws IOException;

    Client findById(String id)throws IOException;

    Client findByIdentificationAndIdentificationType(String identification, String identificationType)throws IOException;

    Client saveClient(Client client, MultipartFile multipartFile) throws IOException;

    String deleteClient(String identification)throws IOException;

    Client updateClient(String  identification,Client clientRequestDTO)throws IOException;

}
