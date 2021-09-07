package com.udea.app.services;

import com.udea.app.models.Pet;
import com.udea.app.repository.IPetRepository;
import com.udea.app.services.dtos.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pet")
@RestController
public class PetService {

    @Autowired
    IPetRepository petRepository;

    @PostMapping
    public ResponseEntity createPet(@RequestBody Pet pet){

        if(Objects.isNull(pet.getName()) || Objects.isNull(pet.getBreed()) || Objects.isNull(pet.getAge()) ||
                Objects.isNull(pet.getEmailOwner()) || Objects.isNull(pet.getNameOwner()) || Objects.isNull(pet.getSize())){
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message("Error: Faltan datos para el registro")
                            .build());
        }

        pet.setCode(UUID.randomUUID().toString());
        petRepository.save(pet);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(MessageResponse.builder()
                        .message("La mascota se a creado exitosamente")
                        .build());
    }

    @PutMapping
    public ResponseEntity updatePet(@RequestBody Pet pet){

        if (Objects.isNull(pet.getId()) || pet.getId().isEmpty()){
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message("Error: es necesario el id de la mascota")
                            .build());
        }

        Pet petMongo = petRepository.findById(pet.getId())
                .orElse(Pet.builder().build());

        if(Objects.isNull(petMongo.getName())){
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message("Error: la mascota no existe")
                            .build());
        }

        petRepository.save(pet);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(MessageResponse.builder()
                        .message("La mascota se a actualizado exitosamente")
                        .build());
    }
}
