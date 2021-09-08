package com.udea.app.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "pet")
public class Pet {

    @Id
    private String id;
    private String name;
    private String breed;
    private String size;
    private String age;
    private String code;
    private String emailOwner;
    private String nameOwner;
    private String care;
    private String paymentMethod;
    private List<String> services;
}
