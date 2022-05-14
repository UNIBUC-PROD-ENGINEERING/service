package ro.unibuc.slots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.slots.data.InformationEntity;
import ro.unibuc.slots.data.InformationRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = InformationRepository.class)
public class HelloApplication {
	private static long frameCount = 0;

	@Autowired
	private InformationRepository informationRepository;

	public static void main(String[] args) {
		try (var context = SpringApplication.run(HelloApplication.class, args)) {
			while (context.isActive()) {
				frameCount = frameCount >= 0 ? frameCount + 1 : 0;
			}
		}
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));
	}

}
