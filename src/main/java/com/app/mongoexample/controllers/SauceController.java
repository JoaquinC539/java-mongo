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
import com.app.mongoexample.models.Sauce;
import com.app.mongoexample.services.SauceService;



@RestController
@RequestMapping("/api/sauce")
public class SauceController {

    public SauceService sauceService;

    public SauceController(SauceService sauceService){
        this.sauceService=sauceService;
    }
    @GetMapping
    public ResponseEntity<List<Sauce>> index(){
        return ResponseEntity.ok().body(this.sauceService.index());
        // return this.sauceService.index();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Sauce> get(@PathVariable("id") String id){
        Sauce sauce=this.sauceService.getById(id);
        return sauce!=null ? ResponseEntity.ok().body(sauce) : ResponseEntity.status(404).body(null);
    }
    @PostMapping()
    public ResponseEntity<Sauce> save(@RequestBody Sauce sauce){
        Sauce saucesaved=this.sauceService.save(sauce);
        return ResponseEntity.status(HttpStatus.CREATED).body(saucesaved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Sauce> update(@PathVariable("id") String id,@RequestBody Sauce sauce){
        Sauce sauceUpdated=this.sauceService.update(id, sauce);
        return ResponseEntity.ok().body(sauceUpdated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable("id") String id){
        Integer deleted=this.sauceService.delete(id);
        return ResponseEntity.ok().body(deleted);
    }  
}
