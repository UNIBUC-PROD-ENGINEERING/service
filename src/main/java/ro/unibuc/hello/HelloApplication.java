package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import ro.unibuc.hello.data.TaskRepository;
import ro.unibuc.hello.data.TaskEntity;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = TaskRepository.class)
public class HelloApplication {

	@Autowired
	private TaskRepository entityRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		// entityRepository.deleteAll();
		// entityRepository.save(new TaskEntity("Implement a functionality for the project", "high"));
	}

}
