package com.udea.app.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "rol")
public class Rol {

    private String id;
    private String name;

}
