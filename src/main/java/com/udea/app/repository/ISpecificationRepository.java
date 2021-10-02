package com.udea.app.repository;

import com.udea.app.models.Specification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ISpecificationRepository extends MongoRepository<Specification, String> {

    Specification findByCod(String cod);
}
