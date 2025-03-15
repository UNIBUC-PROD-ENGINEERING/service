package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;

import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.repositories.UserRepository;
import ro.unibuc.hello.data.LocationEntity;
import ro.unibuc.hello.repositories.LocationRepository;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.data.SongEntity;
import ro.unibuc.hello.repositories.SongRepository;
import ro.unibuc.hello.data.TaskEntity;
import ro.unibuc.hello.repositories.TaskRepository;
import ro.unibuc.hello.data.FoodEntity;
import ro.unibuc.hello.repositories.FoodRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {
        InformationRepository.class, UserRepository.class, LocationRepository.class, PartyRepository.class,
        SongRepository.class, TaskRepository.class, FoodRepository.class
})

public class PartyPlanningApp {

	@Autowired
	private InformationRepository informationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private PartyRepository partyRepository;
    @Autowired
    private SongRepository songRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private FoodRepository foodRepository;

	public static void main(String[] args) {
		SpringApplication.run(PartyPlanningApp.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
        userRepository.deleteAll();
        locationRepository.deleteAll();
        partyRepository.deleteAll();
        songRepository.deleteAll();
        taskRepository.deleteAll();
        foodRepository.deleteAll();
		
        userRepository.save(new UserEntity("John Doe", "john@example.com", "securepassword"));
        locationRepository.save(new LocationEntity("Club X", "123 Party Street", 50, 100));
        foodRepository.save(new FoodEntity("Pizza", 20, 5, 50));
	}

}
