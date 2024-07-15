package com.app.mongoexample.services;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import java.lang.reflect.Type;;

@Service
public class MongoService {
    private final MongoTemplate mongoTemplate;
    private final Gson gson = new Gson();
    private static final Type mapType=new TypeToken<Map<String,Object>>(){}.getType();

    public MongoService(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }
    public List<Map<String,Object>> query(String collection,List<Document> aggregateQuery){
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            AggregateIterable<Document> resultQuery=mongoTemplate.getCollection(collection).aggregate(aggregateQuery).allowDiskUse(true);
            MongoCursor<Document> iterator=resultQuery.iterator();
            while (iterator.hasNext()) {
                Document doc=iterator.next();
                String json=doc.toJson();
                Map<String,Object> jsonObject=gson.fromJson(json, mapType);
                resultList.add(jsonObject);
            }

            return resultList;
        } catch (Exception e) {
            return null;
        }
    }
}
