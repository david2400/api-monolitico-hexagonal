package com.arquitectura.monolitico.api.app.infrastructure.db.springdata.mysql.dbo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "client")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, length = 45)
    private String name;

    @Column( nullable = false, length = 45)
    private String lastName;

    @Column( nullable = false,length = 3)
    private String age;

    @Column(nullable = false, length = 20,unique = true)
    private String identification;

    @Column( nullable = false, length = 100,unique = true)
    private String mail;

    @ManyToOne()
    @JoinColumn(name = "id_city")
    private CityEntity city;

    @ManyToOne()
    @JoinColumn(name = "id_identification_type")
    private IdentificationTypeEntity identificationType;


}