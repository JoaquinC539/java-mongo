package com.app.mongoexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import java.util.logging.Logger;
import java.util.logging.Level;
@SpringBootApplication
public class MongoexampleApplication {

	public static void main(String[] args) {
		Logger mongoLogger=Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.WARNING);
		SpringApplication.run(MongoexampleApplication.class, args);
	}

}
