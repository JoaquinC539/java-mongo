package com.app.mongoexample.services;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.app.mongoexample.models.Sauce;

import com.app.mongoexample.repositories.SauceRepository;

@Service
public class SauceService {
    private final SauceRepository sauceRepository;

    public SauceService(SauceRepository sauceRepository){
        this.sauceRepository=sauceRepository;
    }

    public List<Sauce> index(){
        return sauceRepository.findAll();
    }
    public Sauce getById(String id){
        Optional<Sauce> optSauce=this.sauceRepository.findById(id);
        return optSauce.isPresent() ? optSauce.get() : null;
    }
    public Sauce save(Sauce sauce){
        return this.sauceRepository.save(sauce);
    }
    public Sauce update(String id,Sauce sauce){
        Optional<Sauce> optSauce=this.sauceRepository.findById(id);
        if(optSauce.isPresent()){
            Sauce sauceUpdated=optSauce.get();
            sauceUpdated.setName(sauce.getName());
            return sauceRepository.save(sauceUpdated);
        }else{
            return null;
        }
    }
    public Integer delete(String id){
        sauceRepository.deleteById(id);
        return 1;
    }

}
