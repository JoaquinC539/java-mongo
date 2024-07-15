package com.app.mongoexample.services;

import org.bson.Document;
import org.springframework.web.bind.annotation.RestController;

import com.app.mongoexample.repositories.PizzaRepository;
import com.app.mongoexample.repositories.SauceRepository;
import com.app.mongoexample.repositories.ToppingRespository;
import com.app.mongoexample.utils.AggregateInerface;
// import com.app.mongoexample.utils.MongoQuerier;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.app.mongoexample.models.Pizza;
import com.app.mongoexample.models.PizzaDto;
import com.app.mongoexample.models.Sauce;
import com.app.mongoexample.models.Topping;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;


@RestController
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final SauceRepository sauceRepository;
    private final ToppingRespository toppingRespository;

    private final MongoService mongoService;    

    public PizzaService(PizzaRepository pizzaRepository, SauceRepository sauceRepository,
            ToppingRespository toppingRespository, MongoService mongoService) {
        this.pizzaRepository = pizzaRepository;
        this.sauceRepository = sauceRepository;
        this.toppingRespository = toppingRespository;
        this.mongoService=mongoService;
    }

    public List<Pizza> index() {

        return pizzaRepository.findAll();
    }

    public Pizza getById(String id) {
        Optional<Pizza> optPizza = pizzaRepository.findById(id);
        return optPizza.orElse(null);
    }

    public Pizza save(PizzaDto pizzadto) {
        Optional<Sauce> optSauce = sauceRepository.findById(pizzadto.getSauce());
        if (optSauce.isPresent()) {
            List<Topping> toppings = toppingRespository.findAllById(pizzadto.getToppings());
            Pizza pizza = new Pizza();
            pizza.setName(pizzadto.getName());
            pizza.setPrice(pizzadto.getPrice());
            pizza.setSauce(optSauce.get());
            pizza.setToppings(toppings);
            return pizzaRepository.save(pizza);
        }

        return null;

    }

    public Pizza update(String id, PizzaDto pizzadto) {
        Optional<Pizza> optPizza = pizzaRepository.findById(id);
        if (optPizza.isPresent()) {
            Optional<Sauce> optSauce = sauceRepository.findById(pizzadto.getSauce());
            if (optSauce.isPresent()) {
                List<Topping> toppings = toppingRespository.findAllById(pizzadto.getToppings());
                Pizza updatedPizza = optPizza.get();
                updatedPizza.setName(pizzadto.getName());
                updatedPizza.setPrice(pizzadto.getPrice());
                updatedPizza.setToppings(toppings);
                updatedPizza.setSauce(optSauce.get());
                return pizzaRepository.save(updatedPizza);

            }
            return null;
        } else {
            return null;
        }
    }

    public Integer delete(String id) {
        pizzaRepository.deleteById(id);
        return 1;
    }

   

    public List<Map<String,Object>> aggre() {

        

        AggregateInerface closure=()->{
            List<Document> query = Arrays.asList(new Document("$lookup",
                new Document("from", "sauces")
                        .append("localField", "sauce")
                        .append("foreignField", "_id")
                        .append("as", "sauce")),
                new Document("$unwind",
                        new Document("path", "$sauce")
                                .append("preserveNullAndEmptyArrays", true)),
                new Document("$lookup",
                        new Document("from", "toppings")
                                .append("localField", "toppings")
                                .append("foreignField", "_id")
                                .append("as", "toppings")),
                new Document("$project",
                        new Document("_id", 1L)
                                .append("name", 1L)
                                .append("price", 1L)
                                .append("sauce", "$sauce.name")
                                .append("totalPrice",
                                        new Document("$add", Arrays.asList("$price", 2L)))
                                .append("tax",
                                        new Document("$add", Arrays.asList(0L, 2L)))
                                .append("toppings",
                                        new Document("$map",
                                                new Document("input", "$toppings")
                                                        .append("as", "toppings")
                                                        .append("in",
                                                                new Document("name", "$$toppings.name"))))));
        return mongoService.query("pizzas", query);
        };
        
        List<Map<String,Object>> results=closure.getResults();
        Gson gson=new Gson();
        String jsonstr=gson.toJson(results);
        JsonArray jsonArray=gson.fromJson(jsonstr,JsonArray.class);
        System.out.println(jsonArray.get(0).getAsJsonObject().get("toppings").getAsJsonArray().get(1).getAsJsonObject().get("name"));
        

        
        return results;
    }
}
