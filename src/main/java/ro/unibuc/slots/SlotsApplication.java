package ro.unibuc.slots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.slots.data.InformationEntity;
import ro.unibuc.slots.data.InformationRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = InformationRepository.class)
public class SlotsApplication {
	private static long frameCount = 0;

	private final InformationRepository informationRepository;

	public SlotsApplication(final InformationRepository informationRepository) {
		this.informationRepository = informationRepository;
	}

	public static void main(String[] args) {
		try (var context = SpringApplication.run(SlotsApplication.class, args)) {
			while (context.isActive()) {
				frameCount = frameCount >= 0 ? frameCount + 1 : 0;
			}
		}
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity(
				"Overview",
				"This is an example of using a data storage engine " +
						"running separately from our applications server"
		));
	}
}
