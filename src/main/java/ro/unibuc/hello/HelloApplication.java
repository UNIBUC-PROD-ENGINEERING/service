package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.*;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = NewsRepository.class)
public class HelloApplication {

	@Autowired
	private InformationRepository informationRepository;
	@Autowired
	private NewsRepository newsRepository;
	@Autowired
	private DonationRepository donationRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		//donationRepository.deleteAll();
		//donationRepository.save(new Donation("Sender",0,true));
		/*informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));*/
		// newsRepository.deleteAll();

	}

}
