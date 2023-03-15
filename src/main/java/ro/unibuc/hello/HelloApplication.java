package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.CarRepository;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.*;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = InformationRepository.class)
public class HelloApplication {

	@Autowired
	private InformationRepository informationRepository;

	@Autowired
	private CarRepository carRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));

		carRepository.deleteAll();
		carRepository.save(new CarEntity( "BMW", "Sedan", 2022, "Euro6",30000));
		carRepository.save(new CarEntity( "Volvo", "SUV", 2022, "Euro6",50000));
		carRepository.save(new CarEntity( "Renault", "Sedan", 2022, "Euro6",18000));
		carRepository.save(new CarEntity( "Mercedes", "Sedan", 2022, "Euro6",90000));
		carRepository.save(new CarEntity( "Dacia", "SUV", 2022, "Euro6",15000));


	}

}
