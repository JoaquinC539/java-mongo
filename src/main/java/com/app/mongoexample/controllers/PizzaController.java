package com.app.mongoexample.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.mongoexample.models.Pizza;
import com.app.mongoexample.models.PizzaDto;
import com.app.mongoexample.services.PizzaService;
import java.util.List;

@RestController
@RequestMapping("/api/pizza")
public class PizzaController {
    
    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService){
        this.pizzaService=pizzaService;
    }
    @GetMapping()
    public ResponseEntity<List<Pizza>> index(){
        return ResponseEntity.ok().body(pizzaService.index());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pizza> get(@PathVariable("id") String id){
        Pizza pizza = this.pizzaService.getById(id);
        return pizza != null ? ResponseEntity.ok().body(pizza) : ResponseEntity.status(404).body(null);
    }

    @PostMapping
    public ResponseEntity<Pizza> save(@RequestBody PizzaDto pizzadto){
        Pizza savedPizza = this.pizzaService.save(pizzadto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPizza);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> update(@PathVariable("id") String id, @RequestBody PizzaDto pizza){
        Pizza updatedPizza = this.pizzaService.update(id, pizza);
        return ResponseEntity.ok().body(updatedPizza);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") String id){
        Integer deleted = this.pizzaService.delete(id);
        return ResponseEntity.ok().body(deleted);
    }
    @GetMapping("/agg")
    public Object agg(){
       Object a= pizzaService.aggre();
       
        return ResponseEntity.ok().body(a);
    }
}
