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

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            //String token = getJWTToken(user.getEmail());
            return new ResponseEntity(TokenDTO.builder()
                    .firstName(userLogin.getFirstName())
                    .lastName(user.getLastName())
                    //.token(token)
                    .build(), HttpStatus.OK);

        }
        return new ResponseEntity("El usuario y/o la contraseña son invalidos", HttpStatus.BAD_REQUEST);

    }

    /*private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }*/


}
