package com.udea.app.services;

import com.udea.app.models.User;
import com.udea.app.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping("/user")
@RestController
public class UserService {

    @Autowired
    private IUserRepository iUserRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User userNew = iUserRepository.findByEmail(user.getEmail());

        if (Objects.isNull(userNew)){
            iUserRepository.save(user);
            return new ResponseEntity<>("El usuario se a creado exitosamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public <T> ResponseEntity<T> login(@RequestBody User user) {
        User userLogin = iUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

        if (Objects.nonNull(userLogin)){
            return new ResponseEntity(TokenDTO.builder()
                    .firstName(userLogin.getFirstName())
                    .lastName(user.getLastName())
                    //.token(token)
                    .build(), HttpStatus.OK);

        }
        return new ResponseEntity("El usuario y/o la contraseña son invalidos", HttpStatus.BAD_REQUEST);

    }

}
