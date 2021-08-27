package com.udea.app.services;

import com.udea.app.models.Rol;
import com.udea.app.models.User;
import com.udea.app.repository.IRolRepository;
import com.udea.app.repository.IUserRepository;
import com.udea.app.security.jwt.JwtUtils;
import com.udea.app.security.services.UserDetailsImpl;
import com.udea.app.services.dtos.LoginRequest;
import com.udea.app.services.dtos.MessageResponse;
import com.udea.app.services.dtos.TokenResponse;
import com.udea.app.services.dtos.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
@RestController
public class UserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRolRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UserRequest userRequest) {

        User user = iUserRepository.findByEmail(userRequest.getEmail());

        if (Objects.nonNull(user)) {
            return ResponseEntity
                    .badRequest()
                    .body(MessageResponse.builder()
                            .message("Error: el email ya existe")
                            .build());
        }

        // Create new user's account
        User.UserBuilder userNew = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .username(userRequest.getEmail())
                .password(encoder.encode(userRequest.getPassword()));

        Rol rol;
        if (Objects.isNull(userRequest.getRol())) {
            rol = Rol.builder().id("1").name("USER").build();
        } else {
            switch (userRequest.getRol()) {
                case "admin":
                    rol = Rol.builder().id("2").name("ADMIN").build();
                    break;
                case "empl":
                    rol = Rol.builder().id("3").name("EMPL").build();
                    break;
                default:
                    rol = Rol.builder().id("1").name("USER").build();
                    break;
            }
        }
        userNew.roles(Collections.singletonList(rol));
        userRepository.save(userNew.build());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(MessageResponse.builder()
                .message("El usuario se a creado exitosamente")
                .build());

    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(TokenResponse.builder()
                .email(userDetails.getEmail())
                .token(jwt)
                .typeToken("Bearer")
                .roles(roles)
                .build());
    }
}
