package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.MovieEntity;
import ro.unibuc.hello.data.MovieRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = InformationRepository.class)
public class HelloApplication {

//	@Autowired
//	private InformationRepository informationRepository;
	@Autowired
	private MovieRepository movieRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		movieRepository.deleteAll();
		movieRepository.save(new MovieEntity("Avatar",
				"Jake Sully lives with his newfound family formed on the extrasolar moon Pandora. Once a familiar threat returns to finish what was previously started, Jake must work with Neytiri and the army of the Na'vi race to protect their home.",
				192));
		movieRepository.save(new MovieEntity("Ford v Ferrari",
				"American car designer Carroll Shelby and driver Ken Miles battle corporate interference and the laws of physics to build a revolutionary race car for Ford in order to defeat Ferrari at the 24 Hours of Le Mans in 1966",
				152));
		movieRepository.save(new MovieEntity("Titanic",
				"A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
				196));
		movieRepository.save(new MovieEntity("Dunkirk",
				"Allied soldiers from Belgium, the British Commonwealth and Empire, and France are surrounded by the German Army and evacuated during a fierce battle in World War II.",
				106));
		movieRepository.save(new MovieEntity("Fight Club",
				"An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.",
				139));
		movieRepository.save(new MovieEntity("Bullet Train",
				"Five assassins aboard a swiftly-moving bullet train find out that their missions have something in common."
				,126));
//		informationRepository.deleteAll();
//		informationRepository.save(new InformationEntity("Overview",
//				"This is an example of using a data storage engine running separately from our applications server"));
	}

}
