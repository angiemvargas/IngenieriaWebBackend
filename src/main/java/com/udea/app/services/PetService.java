package com.udea.app.services;

import com.udea.app.mail.MailService;
import com.udea.app.models.Pet;
import com.udea.app.models.Specification;
import com.udea.app.repository.IPetRepository;
import com.udea.app.repository.ISpecificationRepository;
import com.udea.app.services.dtos.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pet")
@RestController
public class PetService {

    @Autowired
    IPetRepository petRepository;

    @Autowired
    ISpecificationRepository specificationRepository;

    @Autowired
    MailService mailService;

    @PostMapping
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity createPet(@RequestBody Pet pet) {

        if (Objects.isNull(pet.getName()) || Objects.isNull(pet.getBreed()) || Objects.isNull(pet.getAge()) ||
                Objects.isNull(pet.getEmailOwner()) || Objects.isNull(pet.getNameOwner()) || Objects.isNull(pet.getWeight())
                || Objects.isNull(pet.getPaymentMethod())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message("Error: Faltan datos para el registro")
                            .build());
        }

        if (Boolean.TRUE.equals(pet.getIsShower())){

            if (Objects.isNull(pet.getSpecifications())) {
                return ResponseEntity
                        .badRequest()
                        .body(MessageResponse.builder()
                                .message("Error: Faltan especificaciones para el spa")
                                .build());
            } else {
                pet.getSpecifications()
                        .stream()
                        .map(specification -> {
                            Specification obj = specificationRepository.findByCod(specification.getCod());
                            specification.setName(obj.getName());
                            return specification;
                        })
                        .collect(Collectors.toList());
            }
        }

        Pet petSave = petRepository.save(pet);

        String mensaje = "El codigo de su mascota es: ".concat(petSave.getId());
        mailService.sendSimpleMessage(petSave.getEmailOwner(), "Codigo de mascota", mensaje);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(MessageResponse.builder()
                        .message("La mascota se a creado exitosamente")
                        .build());
    }

    @PutMapping
    @PreAuthorize("hasRole('DIRECTOR') or hasRole('PROFESOR')")
    public ResponseEntity updatePet(@RequestBody Pet pet) {

        if (Objects.isNull(pet.getId()) || pet.getId().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message("Error: es necesario el id de la mascota")
                            .build());
        }

        if (Boolean.TRUE.equals(pet.getIsShower())){

            if (Objects.isNull(pet.getSpecifications())) {
                return ResponseEntity
                        .badRequest()
                        .body(MessageResponse.builder()
                                .message("Error: Faltan especificaciones para el spa")
                                .build());
            } else {
                pet.getSpecifications()
                        .stream()
                        .map(specification -> {
                            Specification obj = specificationRepository.findByCod(specification.getCod());
                            specification.setName(obj.getName());
                            return specification;
                        })
                        .collect(Collectors.toList());
            }
        }

        Pet petMongo = petRepository.findById(pet.getId())
                .orElse(Pet.builder().build());

        if (Objects.isNull(petMongo.getName())) {
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

    @GetMapping
    @PreAuthorize("hasRole('DIRECTOR') or hasRole('PROFESOR')")
    public ResponseEntity getAllPets() {

        List<Pet> allPet = petRepository.findAll();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(allPet);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity getPetsById(@PathVariable String id) {

        Pet pet = petRepository.findById(id).orElse(Pet.builder().build());

        if (Objects.isNull(pet.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message("Error: la mascota no existe")
                            .build());
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(pet);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity deletePetsById(@PathVariable String id) {

        Pet pet = petRepository.findById(id).orElse(Pet.builder().build());

        if (Objects.isNull(pet.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message("Error: la mascota no existe")
                            .build());
        }

        petRepository.deleteById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(MessageResponse.builder()
                        .message("La mascota ha sido eliminada con exito")
                        .build());
    }

}
