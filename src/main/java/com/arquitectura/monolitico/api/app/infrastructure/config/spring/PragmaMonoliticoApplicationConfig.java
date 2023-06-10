package com.arquitectura.monolitico.api.app.infrastructure.config.spring;


import com.arquitectura.monolitico.api.app.application.service.ClientService;
import com.arquitectura.monolitico.api.app.application.service.PhotoService;
import com.arquitectura.monolitico.api.app.domain.exceptions.CustomRestExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PragmaMonoliticoApplicationConfig {

    @Bean
    public CustomRestExceptionHandler apiExceptionHandler(){
        return new CustomRestExceptionHandler();
    }

    @Bean
    public ClientService clientService (){
        return new ClientService();
    }

    @Bean
    public PhotoService photoService(){
        return  new PhotoService();
    }

}
