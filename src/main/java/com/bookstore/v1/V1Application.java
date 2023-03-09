package com.bookstore.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class V1Application {

	public static void main(String[] args) {
		SpringApplication.run(V1Application.class, args);
	}


}
