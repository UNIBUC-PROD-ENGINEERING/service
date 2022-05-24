package ro.unibuc.slots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.slots.repo.GameRepository;
import ro.unibuc.slots.repo.InformationRepository;
import ro.unibuc.slots.repo.TurnRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {
		GameRepository.class,
		InformationRepository.class,
		TurnRepository.class
})
public class SlotsApplication {
	private static long frameCount = 0;

	private final GameRepository gameRepository;
	private final InformationRepository informationRepository;
	private final TurnRepository turnRepository;

	public SlotsApplication(
			final GameRepository gameRepository,
			final InformationRepository informationRepository,
			final TurnRepository turnRepository
	) {
		this.gameRepository = gameRepository;
		this.informationRepository = informationRepository;
		this.turnRepository = turnRepository;
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
		System.out.println("Game count: " + gameRepository.count());
		System.out.println("Information count: " + informationRepository.count());
		System.out.println("Turn count: " + turnRepository.count());
	}
}
