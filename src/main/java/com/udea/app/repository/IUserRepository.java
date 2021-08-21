package com.udea.app.repository;

import com.udea.app.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
