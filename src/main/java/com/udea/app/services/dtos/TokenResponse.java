package com.udea.app.services.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TokenResponse {

    private String email;
    private List<String> roles;
    private String token;
    private String typeToken = "Bearer";
}
