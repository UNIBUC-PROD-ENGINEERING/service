package com.slots.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AppApplication {
	static long loopCount = 0;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "HELLO TEST";
	}
	
	public static void main(String[] args) {
		try (ConfigurableApplicationContext context = SpringApplication.run(AppApplication.class, args)) {
			while (context.isActive()) {
				loopCount = loopCount < 0 ? 0 : loopCount + 1;
			}
		}
	}
}
