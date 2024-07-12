package com.app.mongoexample.controllers;

import java.util.List;

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

import com.app.mongoexample.models.Topping;
import com.app.mongoexample.services.ToppingService;

@RestController
@RequestMapping("/api/topping")
public class ToppingController {

    private final ToppingService toppingService;

    public ToppingController(ToppingService toppingService){
        this.toppingService = toppingService;
    }

    @GetMapping
    public ResponseEntity<List<Topping>> index(){
        return ResponseEntity.ok().body(this.toppingService.index());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topping> get(@PathVariable("id") String id){
        Topping topping = this.toppingService.getById(id);
        return topping != null ? ResponseEntity.ok().body(topping) : ResponseEntity.status(404).body(null);
    }

    @PostMapping
    public ResponseEntity<Topping> save(@RequestBody Topping topping){
        Topping savedTopping = this.toppingService.save(topping);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTopping);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topping> update(@PathVariable("id") String id, @RequestBody Topping topping){
        Topping updatedTopping = this.toppingService.update(id, topping);
        return ResponseEntity.ok().body(updatedTopping);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") String id){
        Integer deleted = this.toppingService.delete(id);
        return ResponseEntity.ok().body(deleted);
    }
}
