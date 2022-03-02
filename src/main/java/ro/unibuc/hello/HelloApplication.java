package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.ArtworkRepository;
import ro.unibuc.hello.data.ArtworkEntity;
import ro.unibuc.hello.data.ArtworkRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = ArtworkRepository.class)
public class HelloApplication {

	@Autowired
	private ArtworkRepository artworkRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		artworkRepository.deleteAll();
		artworkRepository.save(new ArtworkEntity("1","The Scream",
				"Edvard Munch",
				"Munch's The Scream is an icon of modern art, " +
						"the Mona Lisa for our time. As Leonardo da Vinci " +
						"evoked a Renaissance ideal of serenity and " +
						"self-control, Munch defined how we see our " +
						"own age - wracked with anxiety and uncertainty.",
				"https://www.edvardmunch.org/images/paintings/the-scream.jpg"));
	}

}
