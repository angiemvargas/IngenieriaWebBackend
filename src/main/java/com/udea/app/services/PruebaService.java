package com.udea.app.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/prueba")
@RestController
public class PruebaService {

    @GetMapping("/hola")
    public String hola(){
        return "Hola";
    }
}
