package com.app.mongoexample.utils;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.lang.reflect.Type;;

public class MongoQuerier {

    private static final Gson gson=new Gson();
    private static final Type mapType=new TypeToken<Map<String,Object>>(){}.getType();
    
    public static List<Map<String,Object>> query(String collection,List<Document> aggregateQuery){
        String uri="mongodb://localhost:27017/";
        try (MongoClient mongoClient=MongoClients.create(uri);) {
            List<Map<String,Object>> resultList=new ArrayList<>();
            MongoDatabase database=mongoClient.getDatabase("pizza_app");
            MongoCollection<Document> collection2=database.getCollection(collection);

            AggregateIterable<Document> resultQuery=collection2.aggregate(aggregateQuery).allowDiskUse(true);

            MongoCursor<Document> iterator=resultQuery.iterator();
            while (iterator.hasNext()) {
                Document doc=iterator.next();
                String json=doc.toJson();
                Map<String,Object> jsonObject=gson.fromJson(json, mapType);
                resultList.add(jsonObject);
            }

            return resultList;
        } catch (Exception e) {
            System.out.println("Error in aggregate query: "+e.getMessage());
            return null;
        }
        

        
    }
    
}
