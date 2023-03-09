package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.Car;
import ro.unibuc.hello.data.CarsRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = CarsRepository.class)
public class HelloApplication {

	@Autowired
	private CarsRepository carsRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		carsRepository.deleteAll();
		carsRepository.save(new Car("1","B100ABC", "60", "Audi", "A5", true));
	}

}
