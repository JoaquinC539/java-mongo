package com.app.mongoexample.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document("pizzas")
@Data
@AllArgsConstructor
public class Pizza {
    
    @Id
    String _id;
    String name;
    Float price;
    @Indexed(unique = false)
    @DocumentReference(collection = "sauces")
    Sauce sauce;
    @Indexed(unique = false)
    @DocumentReference(collection = "toppings")
    Collection<Topping> toppings;

    public Pizza(){}

}
