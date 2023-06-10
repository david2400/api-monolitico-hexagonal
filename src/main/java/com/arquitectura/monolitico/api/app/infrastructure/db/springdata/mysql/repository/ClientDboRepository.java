package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.repository;

import com.arquitectura.monolitico.api.app.application.repository.ClientRepository;
import com.arquitectura.monolitico.api.app.domain.entity.City;
import com.arquitectura.monolitico.api.app.domain.entity.Client;
import com.arquitectura.monolitico.api.app.domain.entity.IdentificationType;
import com.arquitectura.monolitico.api.app.domain.entity.Photo;
import com.arquitectura.monolitico.api.app.domain.exceptions.client.*;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.mapper.PhotoEntityMapper;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.repository.SpringDataPhotoRepository;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.mapper.ClientEntityMapper;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.mapper.IdentificationTypeEntityMapper;
import com.arquitectura.monolitico.api.app.domain.exceptions.IdentificationType.IdentificationTypeIdNumberException;
import com.arquitectura.monolitico.api.app.domain.exceptions.IdentificationType.IdentificationTypeNotFoundException;
import com.arquitectura.monolitico.api.app.domain.exceptions.city.CityNotFoundException;
import com.arquitectura.monolitico.api.app.domain.exceptions.global.GlobalDataRequiredException;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ClientDboRepository  {


    private final SpringDataClientRepository clientRepository;

    private final SpringDataCityRepository cityRepository;

    private final SpringDataPhotoRepository photoRepository;

    private final SpringDataIdentificationTypeRepository identificationTypeRepository;

    private  final ClientEntityMapper clientEntityMapper;

    private  final IdentificationTypeEntityMapper identificationTypeEntityMapper;

    private final PhotoEntityMapper photoEntityMapper;



    @Transactional(readOnly=true)
    public List<Client> listAllClients() {
        return clientEntityMapper.toDomain(clientRepository.findAll());
    }


    @Transactional(readOnly=true)
    public List<Client> listAllClientsIdentificationType(String idIdentificationType) throws IOException {
        if(idIdentificationType != null){
            try {
                return ClientEntityMapper.INSTANCE.toDomain(this.clientRepository.findByIdentificationType(identificationTypeEntityMapper.toDbo(IdentificationType.builder().id(Long.parseLong(idIdentificationType)).build())).get());
            }catch (NumberFormatException ex){
                throw  new IdentificationTypeIdNumberException(idIdentificationType);
            }
        }else {
            throw  new GlobalDataRequiredException();
        }
    }


    @Transactional(readOnly=true)
    public Client findById(String id) throws IOException {
        if(id != null){
            Long idLong;
            try {
                idLong = Long.parseLong(id);
            }catch (NumberFormatException ex){
                throw  new ClientIdNumberException(id);
            }
            return clientEntityMapper.toDomain(this.clientRepository.findById(idLong).orElseThrow(()->new ClientNotFoundIdException(idLong)));
        }else{
            throw  new GlobalDataRequiredException();
        }
    }


    @Transactional(readOnly=true)
    public Client findByIdentificationAndIdentificationType(String identification, String identificationType) throws IOException {
        if ( identification != null && identificationType !=null){
            try {
                return clientEntityMapper.toDomain(this.clientRepository.findByIdentificationAndIdentificationType(identification, identificationTypeEntityMapper.toDbo(IdentificationType.builder().id(Long.parseLong(identificationType)).build())).orElseThrow(() -> new ClientNotFoundIdenTypeException(identification, Long.parseLong(identificationType))));
            }catch (NumberFormatException ex){
                throw  new ClientIdentificationTypeNumberException(identificationType);
            }
        }else{
            throw  new GlobalDataRequiredException();
        }
    }


    @Transactional()
    public Client saveClient(Client client, MultipartFile multipartFile) throws IOException {

        if(client != null){

            // verifica si existe la identificacion
            if(this.clientRepository.findByIdentification(client.getIdentification()).isPresent()){
                throw new ClientDataIdentificationExistException(client.getIdentification());
            }
            // verifica si existe el correo
            if(this.clientRepository.findByMail(client.getMail()).isPresent()){
                throw new ClientDataMailExistException(client.getMail());
            }

            // verifica si  no existe el tipo de identificacion por el id
            if(this.identificationTypeRepository.findById(client.getIdentificationType().getId()).isEmpty()){
                throw  new IdentificationTypeNotFoundException(client.getIdentificationType().getId());
            }
            // verifica si no existe la ciudad por el id
            if(this.cityRepository.findById(client.getCity().getId()).isEmpty()){
                throw  new CityNotFoundException(client.getCity().getId());
            }

            int age=Integer.parseInt(client.getAge());
            int minAge=18;
            int maxAge=130;
            if(age < minAge || age > maxAge){
                throw new ClientAgeException();
            }
            if (multipartFile.isEmpty()){
                return clientEntityMapper.toDomain(this.clientRepository.save(clientEntityMapper.toDbo(client)));
            }
            Client clientSave=  clientEntityMapper.toDomain(this.clientRepository.save(clientEntityMapper.toDbo(client)));
            Photo photoSave= Photo.builder()
                    .idClient(clientSave.getId())
                    .base64(Base64.getEncoder().encodeToString(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()).getData()))
                    .build();
            this.photoRepository.save(photoEntityMapper.toDbo(photoSave));
            return clientSave;
        }else{
            throw  new GlobalDataRequiredException();

        }
    }


    @Transactional()
    public String deleteClient(String identification) throws IOException {
        Client client=clientEntityMapper.toDomain(this.clientRepository.findByIdentification(identification).orElseThrow(()-> new ClientNotFoundIdentificationException(identification)));
        Optional<Photo> photo = Optional.ofNullable(photoEntityMapper.toDomain(this.photoRepository.findByIdClient(client.getId()).get()));
        this.clientRepository.delete(ClientEntityMapper.INSTANCE.toDbo(client));
        if (photo.isEmpty()){
            return "removed client with document "+client.getIdentification();
        }else{
            this.photoRepository.delete(photoEntityMapper.toDbo(photo.get()));
            return "removed client and the photo with document "+client.getIdentification();
        }
    }


    @Transactional()
    public Client updateClient(String identification, Client clientRequestDTO) throws IOException {

        if(clientRequestDTO != null && identification !=null){
            Client clientFound = ClientEntityMapper.INSTANCE.toDomain( this.clientRepository.findByIdentification(identification).orElseThrow(()-> new ClientNotFoundIdentificationException(identification)));
            //verifica si se modifico la identificacion
            if(!clientRequestDTO.getIdentification().equals(clientFound.getIdentification())){
                // verifica si existe la identificacion
                if(this.clientRepository.findByIdentification(clientRequestDTO.getIdentification()).isPresent()){
                    throw new ClientDataIdentificationExistException(clientRequestDTO.getIdentification());
                }
            }
            //varifica si se modifico el correo
            if (!clientRequestDTO.getMail().equals(clientFound.getMail())){
                // verifica si existe el correo
                if(this.clientRepository.findByMail(clientRequestDTO.getMail()).isPresent()){
                    throw new ClientDataMailExistException(clientRequestDTO.getMail());
                }
            }
            // verifica si  existe el tipo de identificacion por el id
            if(this.identificationTypeRepository.findById(clientRequestDTO.getIdentificationType().getId()).isEmpty()){
                throw new IdentificationTypeNotFoundException(clientRequestDTO.getIdentificationType().getId());
            }
            // verifica si  existe la ciudad por el id
            if(this.cityRepository.findById(clientRequestDTO.getCity().getId()).isEmpty()){
                throw new CityNotFoundException(clientRequestDTO.getCity().getId());
            }
            int age=Integer.parseInt(clientRequestDTO.getAge());
            int minAge=18;
            int maxAge=130;
            if(age < minAge || age > maxAge){
                throw new ClientAgeException();
            }
            // lo hago creando una entidad , ya que si modifico el identificador (id) de  city de clientFound aparece este error,JpaSystemException  identifier of an instance altered from X to Y
            City city =new City();
            city.setId(clientRequestDTO.getCity().getId());
            IdentificationType identificationType=new IdentificationType();
            identificationType.setId(clientRequestDTO.getIdentificationType().getId());
            clientFound.setName(clientRequestDTO.getName());
            clientFound.setLastName(clientRequestDTO.getLastName());
            clientFound.setAge(clientRequestDTO.getAge());
            clientFound.setIdentification(clientRequestDTO.getIdentification());
            clientFound.setMail(clientRequestDTO.getMail());
            clientFound.setCity(city);
            clientFound.setIdentificationType(identificationType);
            return  clientEntityMapper.toDomain(this.clientRepository.save(clientEntityMapper.toDbo(clientFound)));
        }else {
            throw new GlobalDataRequiredException();
        }
    }
}
