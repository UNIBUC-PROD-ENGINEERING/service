package ro.unibuc.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.repositories.*;
import ro.unibuc.hello.models.InformationEntity;
import ro.unibuc.hello.service.MockDbService;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "ro.unibuc.hello.repositories")
public class HelloApplication {

	@Autowired
	private InformationRepository informationRepository;

	@Autowired
	private MockDbService mockDbService;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}


	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));

		mockDbService.checkDatabase();
	}

}
