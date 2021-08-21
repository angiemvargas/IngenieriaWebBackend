package com.udea.app.services;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {

    private String firstName;
    private String lastName;
    private String token;
}
