package com.udea.app.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Lugar {
    private String city;
    private String department;
    private String country;
}
