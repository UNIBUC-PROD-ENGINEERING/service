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

	private CarRepository carRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));
//		carRepository.deleteAll();

		CarEntity car1 = new CarEntity("1", "BMW", "Sedan", 2022, "Euro6",30000);
//		CarEntity car2 = new CarEntity("2", "Volvo", "SUV", 2022, "Euro6",50000);
//		CarEntity car3 = new CarEntity("3", "Renault", "Sedan", 2022, "Euro6",18000);
//		CarEntity car4 = new CarEntity("4", "Mercedes", "Sedan", 2022, "Euro6",90000);
//		CarEntity car5 = new CarEntity("5", "Dacia", "SUV", 2022, "Euro6",15000);
//
//
//
//		car1 = carRepository.save(car1);
//		car2 = carRepository.save(car2);
//		car3 = carRepository.save(car3);
//		car4 = carRepository.save(car4);
//		car5 = carRepository.save(car5);

	}

}
