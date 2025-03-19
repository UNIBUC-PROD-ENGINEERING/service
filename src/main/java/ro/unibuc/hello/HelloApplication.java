package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.GameRepository;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.SubscriptionEntity;
import ro.unibuc.hello.data.SubscriptionRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {InformationRepository.class, GameRepository.class})

public class HelloApplication {

	// @Autowired
	// private InformationRepository informationRepository;
	@Autowired
	private GameRepository gamesRepository;
	@Autowired
	private SubscriptionRepository subscriptionsRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		
		gamesRepository.deleteAll();
		gamesRepository.save(new GameEntity("Balatro", 1));

		gamesRepository.save(new GameEntity("Half-Life", 1));
		gamesRepository.save(new GameEntity("Half-Life 2", 1));
		gamesRepository.save(new GameEntity("Minecraft", 2));
		gamesRepository.save(new GameEntity("Half-Life:Alyx", 3));
		gamesRepository.save(new GameEntity("Cyberpunk 2077", 3));

		subscriptionsRepository.deleteAll();
		subscriptionsRepository.save(new SubscriptionEntity(1, 70));
		subscriptionsRepository.save(new SubscriptionEntity(2, 90));

		userRepository.deleteAll();
		userRepository.save(new UserEntity("Mihaitza","parola1"));
		userRepository.save(new UserEntity("Andreiutzu","parola2"));
		userRepository.save(new UserEntity("Ionel","parola3"));
	}
}
