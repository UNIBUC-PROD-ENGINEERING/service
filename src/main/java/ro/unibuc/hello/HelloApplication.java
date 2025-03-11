package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.GameRepository;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {InformationRepository.class, GameRepository.class})

public class HelloApplication {

	// @Autowired
	// private InformationRepository informationRepository;
	@Autowired
	private GameRepository gamesRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		// informationRepository.deleteAll();
		// informationRepository.save(new InformationEntity("Overview",
		// 		"This is an example of using a data storage engine running separately from our applications server"));
		
		gamesRepository.deleteAll();
		gamesRepository.save(new GameEntity("Half-Life 2", 1));
		gamesRepository.save(new GameEntity("Balatro", 1));
		System.out.println("Database initialization complete!");
	}

}
