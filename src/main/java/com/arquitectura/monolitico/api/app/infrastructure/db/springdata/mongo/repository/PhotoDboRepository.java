package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.repository;


import com.arquitectura.monolitico.api.app.domain.entity.Client;
import com.arquitectura.monolitico.api.app.domain.entity.Photo;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.mapper.PhotoEntityMapper;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.mapper.ClientEntityMapper;
import com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.repository.SpringDataClientRepository;
import com.arquitectura.monolitico.api.app.domain.exceptions.client.ClientNotFoundIdentificationException;
import com.arquitectura.monolitico.api.app.domain.exceptions.global.GlobalDataRequiredException;
import com.arquitectura.monolitico.api.app.domain.exceptions.photo.PhotNotFoundIdentificationException;
import com.arquitectura.monolitico.api.app.domain.exceptions.photo.PhotoClientExistException;
import com.arquitectura.monolitico.api.app.domain.exceptions.photo.PhotoNotFoundIdException;
import com.arquitectura.monolitico.api.app.domain.exceptions.photo.PhotoNotFoundUpdateException;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PhotoDboRepository  {

    private final SpringDataPhotoRepository photoRepository;

    private final SpringDataClientRepository clientRepository;

    private final PhotoEntityMapper photoEntityMapper;

    private final ClientEntityMapper clientEntityMapper;



    @Transactional(readOnly=true)
    public List<Photo> listAllPhotos() {
        return photoEntityMapper.toDomain(photoRepository.findAll());
    }


    @Transactional(readOnly=true)
    public Photo getPhotoByClientIdentification(String identification) throws IOException {
        if(identification != null) {

            Client client = clientEntityMapper.toDomain(this.clientRepository.findByIdentification(identification).orElseThrow(()-> new ClientNotFoundIdentificationException(identification)));
            Photo photo = photoEntityMapper.toDomain(this.photoRepository.findByIdClient(client.getId()).orElseThrow(()-> new PhotNotFoundIdentificationException(identification))) ;
            return photoEntityMapper.toDomain(photoEntityMapper.toDbo(photo));

        }else {
            throw  new GlobalDataRequiredException();
        }
    }


    @Transactional(readOnly=true)
    public Photo getPhotoById(String id) throws IOException {
        if (id != null){
            return photoEntityMapper.toDomain(this.photoRepository.findById(id).orElseThrow(()-> new PhotoNotFoundIdException(id)));
        }else{
            throw  new GlobalDataRequiredException();
        }

    }


    @Transactional
    public Photo save(MultipartFile multipartFile, String identification) throws IOException {
        if( !multipartFile.isEmpty() && identification !=null){

            Client client = clientEntityMapper.toDomain( this.clientRepository.findByIdentification(identification).orElseThrow(()-> new ClientNotFoundIdentificationException(identification)));


            //valida si el cliente no tiene una foto
            if(this.photoRepository.findByIdClient(client.getId()).isPresent()){
                throw new PhotoClientExistException(identification);
            }

            Photo photoSave = Photo.builder()
                    .idClient(client.getId())
                    .base64(Base64.getEncoder().encodeToString( new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()).getData()))
                    .build();

            return photoEntityMapper.toDomain(this.photoRepository.save(photoEntityMapper.toDbo(photoSave)));

        }else{
            throw new GlobalDataRequiredException();
        }
    }


    @Transactional
    public String deleteById(String idPhoto) throws IOException {
        if (idPhoto != null){

            Photo photo = photoEntityMapper.toDomain(this.photoRepository.findById(idPhoto).orElseThrow(()->new PhotoNotFoundIdException(idPhoto)));

            this.photoRepository.delete(photoEntityMapper.toDbo(photo));
            return "removed photo with id "+photo.getId();

        }else{
            throw new GlobalDataRequiredException();
        }
    }


    @Transactional
    public String deleteByIdentification(String identification) throws IOException {

        // tener en cuenta la linea 118 al momento de testear se mandó la excepcion que no era
        if (identification != null){
            Client client = clientEntityMapper.toDomain(this.clientRepository.findByIdentification(identification).orElseThrow(()-> new ClientNotFoundIdentificationException(identification))) ;
            Photo photo = photoEntityMapper.toDomain(this.photoRepository.findByIdClient(client.getId()).orElseThrow(()->new PhotNotFoundIdentificationException(identification)));
            this.photoRepository.delete(photoEntityMapper.toDbo(photo));
            return "photo with id "+photo.getId() + " was removed";

        }else{
            throw new GlobalDataRequiredException();
        }
    }


    @Transactional
    public Photo update(MultipartFile multipartFile, String identification) throws IOException {
        if( !multipartFile.isEmpty() && identification !=null){

            Client client = clientEntityMapper.toDomain(this.clientRepository.findByIdentification(identification).orElseThrow(()->new ClientNotFoundIdentificationException(identification))) ;
            //valida si el cliente tiene una foto
            Photo photoFound= photoEntityMapper.toDomain(this.photoRepository.findByIdClient(client.getId()).orElseThrow(PhotoNotFoundUpdateException::new));

            return photoEntityMapper.toDomain(this.photoRepository.save(photoEntityMapper.toDbo(Photo.builder()
                    .id(photoFound.getId())
                    .idClient(photoFound.getIdClient())
                    .base64(Base64.getEncoder().encodeToString(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()).getData()))
                    .build())));
        }else{
            throw new GlobalDataRequiredException();
        }
    }
}
