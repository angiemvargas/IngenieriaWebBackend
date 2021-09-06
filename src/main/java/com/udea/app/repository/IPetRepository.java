package com.udea.app.repository;

import com.udea.app.models.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPetRepository extends MongoRepository<Pet, String> {

}
