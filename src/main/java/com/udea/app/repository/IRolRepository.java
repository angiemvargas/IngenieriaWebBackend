package com.udea.app.repository;

import com.udea.app.models.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends MongoRepository<Rol, String> {
}
