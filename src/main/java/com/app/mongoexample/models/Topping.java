package com.app.mongoexample.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("toppings")
@Data
public class Topping {
    
    @Id    
    private String _id;
    
    private String name;
    

    public Topping(){};

    public Topping(String name){
        this.name=name;
    }

}