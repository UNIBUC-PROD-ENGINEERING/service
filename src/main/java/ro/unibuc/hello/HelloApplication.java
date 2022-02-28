package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = InformationRepository.class)
public class HelloApplication {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		UserEntity user1 = new UserEntity("Jack", "jack@wmail.com");
		MovieEntity movie1 = new MovieEntity("Avatar", "James Cameron", "James Cameron", 2009, 162);
		ReviewEntity review1 = new ReviewEntity("Great movie", 8);
		ArrayList<ReviewEntity> reviews = new ArrayList<>(){{ add(review1);}};
		ArrayList<MovieEntity> movieWatchList = new ArrayList<>(){{ add(movie1);}};
		ArrayList<UserEntity> userWatchList = new ArrayList<>(){{ add(user1);}};

		movieRepository.deleteAll();
		reviewRepository.deleteAll();
		userRepository.deleteAll();

//		userRepository.save(new UserEntity(user1.getName(), user1.getEmail(), reviews, movieWatchList));
//		movieRepository.save(new MovieEntity(movie1.getTitle(), movie1.getDirector(), movie1.getWriter(), movie1.getYear(), movie1.getDuration(), reviews, userWatchList));
//		reviewRepository.save(new ReviewEntity(review1.getComment(), review1.getScore(), movie1, user1));
	}

}
