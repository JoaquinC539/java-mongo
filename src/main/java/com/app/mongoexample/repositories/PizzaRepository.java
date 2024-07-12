package com.app.mongoexample.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.mongoexample.models.Pizza;

public interface PizzaRepository extends MongoRepository<Pizza,String> {
    
}
