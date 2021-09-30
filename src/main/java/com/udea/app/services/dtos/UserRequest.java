package com.udea.app.services.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String rol;
    private String document;
    private String age;
    private String eps;
    private Integer salary;
}
