package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.IngredientRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = IngredientRepository.class)
public class HelloApplication {

	@Autowired
	private IngredientRepository ingredientRepository;

	public static void main(String[] args) {


		SpringApplication.run(HelloApplication.class, args);
	}



}
