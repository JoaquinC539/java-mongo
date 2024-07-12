package com.app.mongoexample.services;

import org.springframework.web.bind.annotation.RestController;

import com.app.mongoexample.repositories.PizzaRepository;
import com.app.mongoexample.repositories.SauceRepository;
import com.app.mongoexample.repositories.ToppingRespository;
import com.app.mongoexample.models.Pizza;
import com.app.mongoexample.models.PizzaDto;
import com.app.mongoexample.models.Sauce;
import com.app.mongoexample.models.Topping;

import java.util.List;
import java.util.Optional;

@RestController
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final SauceRepository sauceRepository;
    private final ToppingRespository toppingRespository;

    public PizzaService(PizzaRepository pizzaRepository, SauceRepository sauceRepository,
            ToppingRespository toppingRespository) {
        this.pizzaRepository = pizzaRepository;
        this.sauceRepository = sauceRepository;
        this.toppingRespository = toppingRespository;
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
}
