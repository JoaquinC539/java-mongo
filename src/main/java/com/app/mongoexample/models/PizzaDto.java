package com.app.mongoexample.models;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class PizzaDto {
    String _id;
    String name;
    Float price;
    
    String sauce;
    
    Collection<String> toppings;

    public PizzaDto(){}
}
