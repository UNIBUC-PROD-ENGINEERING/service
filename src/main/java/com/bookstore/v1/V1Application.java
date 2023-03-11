package com.bookstore.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class V1Application {
    public static void main(String[] args) {
        SpringApplication.run(V1Application.class, args);
    }
}
