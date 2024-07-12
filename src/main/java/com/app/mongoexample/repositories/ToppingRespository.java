package com.app.mongoexample.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.mongoexample.models.Topping;

@Repository
public interface ToppingRespository extends MongoRepository<Topping,String>{

}

    
