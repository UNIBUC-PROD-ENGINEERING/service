package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.Role;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "ro.unibuc.hello")
@EnableMongoRepositories(basePackages = "ro.unibuc.hello.data")

public class HelloApplication {

	@Autowired
	private InformationRepository informationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		userRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));
		userRepository.save(new UserEntity("admin","admin",passwordEncoder.encode("admin"),Role.ADMIN));
	}

}
