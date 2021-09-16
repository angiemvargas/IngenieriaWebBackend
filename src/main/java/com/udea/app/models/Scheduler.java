package com.udea.app.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "scheduler")
public class Scheduler {

    @Id
    private String id;
    private Date date;
    private String hour;
    private String teacher;
    private String idPet;
    private String title;
    private String namePet;
}
