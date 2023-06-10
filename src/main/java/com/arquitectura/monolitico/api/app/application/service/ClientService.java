package com.arquitectura.monolitico.api.app.application.service;


import com.arquitectura.monolitico.api.app.application.repository.ClientRepository;
import com.arquitectura.monolitico.api.app.domain.entity.Client;
import com.arquitectura.monolitico.api.app.domain.entity.IdentificationType;
import com.arquitectura.monolitico.api.app.domain.entity.Photo;
import com.arquitectura.monolitico.api.app.domain.exceptions.client.*;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.service.PhotoDboService;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service.CityDboService;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service.IdentificationTypeDboService;
import com.arquitectura.monolitico.api.app.domain.exceptions.IdentificationType.IdentificationTypeIdNumberException;
import com.arquitectura.monolitico.api.app.domain.exceptions.global.GlobalDataRequiredException;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.service.ClientDboService;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


/**
 * Se va  a aplicar la logica de negocio
 */


@RequiredArgsConstructor
public class ClientService  implements  ClientRepository{


    @Autowired
    private  ClientDboService clientDboService;

    @Autowired
    private CityDboService cityService;

    @Autowired
    private IdentificationTypeDboService identificationTypeDboService;

    @Autowired
    private PhotoDboService photoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    @Override
    public List<Client> listAllClients() {
        return this.clientDboService.findAll().orElseThrow();
    }
    
    @Override
    public List<Client> listAllClientsIdentificationType(String idIdentificationType) throws IOException {
        if(idIdentificationType != null){
            try {
                return this.clientDboService.findByIdentificationType(IdentificationType.builder().id(Long.parseLong(idIdentificationType)).build()).orElseThrow();
            }catch (NumberFormatException ex){
                throw  new IdentificationTypeIdNumberException(idIdentificationType);
            }
        }else {
            throw  new GlobalDataRequiredException();
        }
    }

    @Override
    public Client findById(String id) throws IOException {
        if(id != null){
            try {
                Long idLong = Long.parseLong(id);
                return this.clientDboService.findByIdNotFound(idLong).orElseThrow();
            }catch (NumberFormatException ex){
                throw  new ClientIdNumberException(id);
            }
        }else{
            throw  new GlobalDataRequiredException();
        }
    }

    @Override
    public Client findByIdentificationAndIdentificationType(String identification, String identificationType) throws IOException {
        if ( identification != null && identificationType !=null){
            try {
                return this.clientDboService.findByIdentificationAndIdentificationTypeNotFound(identification, IdentificationType.builder().id(Long.parseLong(identificationType)).build()).orElseThrow();
            }catch (NumberFormatException ex){
                throw  new ClientIdentificationTypeNumberException(identificationType);
            }
        }else{
            throw  new GlobalDataRequiredException();
        }
    }

    @Override
    public Client saveClient(Client client, MultipartFile multipartFile) throws IOException {

        if(client != null){



            // * verifica si existe la identificacion
            this.clientDboService.validPresentFindByIdentification(client.getIdentification());

            // * verifica si existe el correo
            this.clientDboService.validPresentFindByMail(client.getMail());

            // * verifica si  no existe el tipo de identificacion por el id
            this.identificationTypeDboService.findByIdEmpty(client.getIdentificationType().getId());

            // * verifica si no existe la ciudad por el id
            this.cityService.findByIdEmpty(client.getCity().getId());

            int age=Integer.parseInt(client.getAge());
            int minAge=18;
            int maxAge=130;
            if(age < minAge || age > maxAge){
                throw new ClientAgeException();
            }

            if (multipartFile.isEmpty()){
                return this.clientDboService.save(client).orElseThrow();
            }

            Client clientSave= this.clientDboService.save(client).orElseThrow();

            this.photoService.save(Photo.builder()
                    .idClient(clientSave.getId())
                    .base64(Base64.getEncoder().encodeToString(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()).getData()))
                    .build());

            return clientSave;
        }else{
            throw  new GlobalDataRequiredException();
        }
    }

    @Override
    public String deleteClient(String identification) throws IOException {

        LOGGER.info("entraaaa");

        Client client = this.clientDboService.findByIdentificationNotFound(identification).orElseThrow();

        /*
        ! No está ingresando. 😒
         */
        Optional<Photo> photo = this.photoService.findByIdClient(client.getId());


        this.clientDboService.delete(client);

        if (photo.isEmpty()){
            return "removed client with document "+client.getIdentification();
        }else{
            this.photoService.delete(photo.get());
            return "removed client and the photo with document "+client.getIdentification();
        }
    }

    @Override
    public Client updateClient(String identification, Client clientRequestDTO) throws IOException {
        return  null;
    }
}
