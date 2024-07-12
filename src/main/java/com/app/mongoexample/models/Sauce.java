package com.app.mongoexample.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("sauces")
@Data
public class Sauce {
    
    @Id    
    private String _id;
    private String name;
    

    public Sauce(){};

    public Sauce(String name){
        this.name=name;
    }

}
