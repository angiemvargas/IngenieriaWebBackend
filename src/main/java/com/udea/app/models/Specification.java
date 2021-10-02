package com.udea.app.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "specification")
public class Specification {

    @Id
    private String id;
    private String cod;
    private String name;
}
