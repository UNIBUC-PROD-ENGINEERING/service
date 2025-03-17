package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.repository.BankAccountRepository;
import ro.unibuc.hello.repository.*;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "ro.unibuc.hello")
@EnableMongoRepositories(basePackageClasses = {InformationRepository.class,
											   ClientRepository.class,
											   BankAccountRepository.class,
											   CardRepository.class,
											   GroupRepository.class,
											   TransactionRepository.class}
											   )
public class HelloApplication {

	@Autowired
	private InformationRepository informationRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));
	}

}
