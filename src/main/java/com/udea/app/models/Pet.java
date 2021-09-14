package com.udea.app.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "pet")
public class Pet {

    @Id
    private String id;
    private String name;
    private String breed;
    private String weight;
    private String age;
    private String emailOwner;
    private String nameOwner;
    private String address;
    private Lugar place;
    private String paymentMethod;
    private Boolean isShower;
    private Boolean isEducation;
    private Boolean isNursery;
}
