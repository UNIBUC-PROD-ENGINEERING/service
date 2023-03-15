package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.Avion;
import ro.unibuc.hello.data.AvionRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = AvionRepository.class)
public class HelloApplication {

	@Autowired
	private AvionRepository avionRepository;
	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		if(avionRepository.findAll().isEmpty()) {
			avionRepository.save(new Avion("1", "Bucharest", "Honolulu"));
		}
	}

}
