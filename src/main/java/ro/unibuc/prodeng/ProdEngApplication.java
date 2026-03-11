package ro.unibuc.prodeng;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import ro.unibuc.prodeng.repository.UserRepository;
import ro.unibuc.prodeng.request.CreateTodoRequest;
import ro.unibuc.prodeng.request.RegisterRequest;
import ro.unibuc.prodeng.service.TodoService;
import ro.unibuc.prodeng.service.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories
public class ProdEngApplication {

	@Autowired
	private UserService userService;

	@Autowired
	private TodoService todoService;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProdEngApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		if (userRepository.findByEmail("frodo@theshire.me").isEmpty()) {
			RegisterRequest userRequest = new RegisterRequest("Frodo Baggins", "frodo@theshire.me", "shire123", null, false);
			userService.registerUser(userRequest);
			todoService.createTodo(new CreateTodoRequest("Take the ring to Mordor", "frodo@theshire.me"));
		}
	}
}
