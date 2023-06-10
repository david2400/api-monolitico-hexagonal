package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.config;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.repository")
@ConfigurationProperties("spring.datasource")
@Slf4j
@NoArgsConstructor
@Getter
@Setter
@EnableJpaAuditing
@EntityScan(basePackages = "com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mongo.dbo")
public class SpringDataConfig {
}
