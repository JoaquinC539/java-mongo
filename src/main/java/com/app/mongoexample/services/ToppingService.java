package com.app.mongoexample.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.app.mongoexample.models.Topping;

import com.app.mongoexample.repositories.ToppingRespository;

@Service
public class ToppingService {
    private final ToppingRespository ToppingRespository;

    public ToppingService(ToppingRespository ToppingRespository){
        this.ToppingRespository=ToppingRespository;
    }

    public List<Topping> index(){
        return ToppingRespository.findAll();
    }
    public Topping getById(String id){
        Optional<Topping> optTopping=this.ToppingRespository.findById(id);
        return optTopping.isPresent() ? optTopping.get() : null;
    }
    public Topping save(Topping Topping){
        return this.ToppingRespository.save(Topping);
    }
    public Topping update(String id,Topping Topping){
        Optional<Topping> optTopping=this.ToppingRespository.findById(id);
        if(optTopping.isPresent()){
            Topping ToppingUpdated=optTopping.get();
            ToppingUpdated.setName(Topping.getName());
            return ToppingRespository.save(ToppingUpdated);
        }else{
            return null;
        }
    }
    public Integer delete(String id){
        ToppingRespository.deleteById(id);
        return 1;
    }

}