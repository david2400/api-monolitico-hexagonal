package com.arquitectura.monolitico.api.app.infrastructure.config.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages="com.arquitectura.monolitico.api.app.infrastructure")
@EntityScan(basePackages = "com.arquitectura.monolitico.api.app.domain")
public class PragmaMonoliticoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PragmaMonoliticoApplication.class, args);
	}

}