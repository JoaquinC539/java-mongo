package com.app.mongoexample.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.mongoexample.models.Sauce;

@Repository
public interface SauceRepository extends MongoRepository<Sauce,String>{
    
}
