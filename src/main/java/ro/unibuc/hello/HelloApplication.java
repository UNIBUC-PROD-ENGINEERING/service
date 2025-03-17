package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import ro.unibuc.hello.data.*;
import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "ro.unibuc.hello")
@EnableMongoRepositories(basePackages = "ro.unibuc.hello.data")

public class HelloApplication {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private ToDoListRepository toDoListRepository;

	@Autowired
	private UserListRepository userListRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		userRepository.deleteAll();
		itemRepository.deleteAll();
		requestRepository.deleteAll();
		toDoListRepository.deleteAll();
		userListRepository.deleteAll();

		userRepository.save(new UserEntity("admin","admin",passwordEncoder.encode("admin"),Role.ADMIN));

		try { itemRepository.save(new ItemEntity()); } catch (Exception exception) { }
		try { requestRepository.save(new RequestEntity()); } catch (Exception exception) { }
		try { toDoListRepository.save(new ToDoListEntity()); } catch (Exception exception) { }
		try { userListRepository.save(new UserListEntity()); } catch (Exception exception) { }
	}

}
