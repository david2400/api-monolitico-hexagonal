package com.arquitectura.monolitico.api.app.infrastructure.rest.spring.controller;

import com.arquitectura.monolitico.api.app.application.repository.PhotoRepository;
import com.arquitectura.monolitico.api.app.infrastructure.rest.spring.dto.PhotoDto;
import com.arquitectura.monolitico.api.app.infrastructure.rest.spring.dto.PhotoRequestDTO;
import com.arquitectura.monolitico.api.app.infrastructure.rest.spring.mapper.PhotoMapper;
import com.arquitectura.monolitico.api.app.infrastructure.rest.spring.mapper.PhotoRequestMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT,RequestMethod.PATCH})
@RequestMapping(value = "api/photos", consumes = MediaType.ALL_VALUE)
@Validated
public class PhotoController {

    @Autowired(required=true)
    private PhotoRepository photoService;

    @Operation(summary = "find all photos")
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<List<PhotoDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(PhotoMapper.INSTANCE.toDto(this.photoService.listAllPhotos()));
    }

    @Operation(summary = "get photo by identification client")
    @GetMapping("/identification/{identification}")
    @ResponseBody
    public ResponseEntity<PhotoDto> findPhotoByIdentification(@PathVariable(value="identification") String identification) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(PhotoMapper.INSTANCE.toDto(this.photoService.getPhotoByClientIdentification(identification)));
    }

    @Operation(summary = "get photo by id")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<PhotoDto> findPhotoById(@PathVariable(value="id") String id) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(PhotoMapper.INSTANCE.toDto(this.photoService.getPhotoById(id)));
    }

    @Operation(summary = "save a photo , the client is searched by identification for convenience")
    @PostMapping(value = "/{identification}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<PhotoRequestDTO>   save (@RequestPart(value = "photo") MultipartFile photo, @PathVariable(value="identification") String identification) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(PhotoRequestMapper.INSTANCE.toDto(this.photoService.save(photo,identification)));
    }

    @Operation(summary = "delete  a photo whit id")
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteByPhotoId (@PathVariable(value = "id") String photoId) throws  IOException{
        return ResponseEntity.status(HttpStatus.OK).body(this.photoService.deleteById(photoId));
    }

    @Operation(summary = "delete  a photo whit client identification")
    @DeleteMapping("/identification/{identification}")
    @ResponseBody
    public ResponseEntity<String> deleteByIdentification (@PathVariable(value = "identification") String identification) throws  IOException{
        return ResponseEntity.status(HttpStatus.OK).body(this.photoService.deleteByIdentification(identification));
    }

    @Operation(summary = "update a photo , the client is searched by identification for convenience")
    @PutMapping(value = "/{identification}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<PhotoRequestDTO>   update (@RequestPart(value = "photo") MultipartFile photo, @PathVariable(value="identification") String identification) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(PhotoRequestMapper.INSTANCE.toDto( this.photoService.update(photo,identification)));
    }
}
