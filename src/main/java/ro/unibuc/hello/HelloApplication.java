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

	@Autowired
	private WatchItemRepository watchItemRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		UserEntity user1 = new UserEntity("Wane", "wane@wmail.com");
		UserEntity user2 = new UserEntity("Russell Hobbs", "russelhobbs@yahoo.com");
		UserEntity user3 = new UserEntity("Robert Smith", "robertsmith@yahoo.com");
		UserEntity user4 = new UserEntity("Cassidy Beth", "cassy@gmail.com");
		UserEntity user5 = new UserEntity("Thomas Miller", "tmiller@yahoo.com");

		MovieEntity movie1 = new MovieEntity("Avatar", "James Cameron", "James Cameron", 2009, 162);
		MovieEntity movie2 = new MovieEntity("Amadeus","Milos Forman", "Peter Shaffer", 1984,160);
		MovieEntity movie3 = new MovieEntity("Birdemic:Shock and Terror","James Nguyen", "James Nguyen", 2010, 105);
		MovieEntity movie4 = new MovieEntity("Meet the Parents","Jay Roach","Greg Glienna" ,2000,108);
		MovieEntity movie5 = new MovieEntity("The Way Back","Gavin O'Connor","Brad Ingelsby",2020,108);

		ReviewEntity review1 = new ReviewEntity("Great movie", 8);
		ReviewEntity review2 = new ReviewEntity("Amadeus is a film of high accolade...this film is grand on every level and I'm glad to" +
				"have experienced it",9);
		ReviewEntity review3 = new ReviewEntity("Should have been better.",4);
		ReviewEntity review4 = new ReviewEntity("7",7);
		ReviewEntity review5 = new ReviewEntity("Can this even be considered a movie?!",1);
		ReviewEntity review6 = new ReviewEntity("Star-driven comedy at times offensive and funny",6);
		ReviewEntity review7 = new ReviewEntity("Owes about 55 percent of its charm to De Niro,who can be hilarious" +
				"just sitting there.", 8);
		ReviewEntity review8 = new ReviewEntity("Without a Doubt a Must-See Mvoie",2);
		ReviewEntity review9 = new ReviewEntity("The film's awesome achievemnts will remind audiences how rich and challenging " +
				"a cinematic experience can be",9);
		ReviewEntity review10 = new ReviewEntity("A great movie by actor re-inacting the life of Amadeus(Thanks Tom)!!!!!",10);

		WatchItemEntity watchItem1 = null;
		WatchItemEntity watchItem2 = null;
		WatchItemEntity watchItem3 = null;
		WatchItemEntity watchItem4 = null;
		WatchItemEntity watchItem5 = null;
		WatchItemEntity watchItem6 = null;
		WatchItemEntity watchItem7 = null;
		WatchItemEntity watchItem8 = null;
		WatchItemEntity watchItem9 = null;
		WatchItemEntity watchItem10 = null;
		WatchItemEntity watchItem11 = null;
		WatchItemEntity watchItem12 = null;

		movieRepository.deleteAll();
		reviewRepository.deleteAll();
		userRepository.deleteAll();
		watchItemRepository.deleteAll();

		user1 = userRepository.save(new UserEntity(user1.getName(), user1.getEmail()));
		user2 = userRepository.save(new UserEntity(user2.getName(), user2.getEmail()));
		user3 = userRepository.save(new UserEntity(user3.getName(), user3.getEmail()));
		user4 = userRepository.save(new UserEntity(user4.getName(), user4.getEmail()));
		user5 = userRepository.save(new UserEntity(user5.getName(), user5.getEmail()));

		movie1 = movieRepository.save(new MovieEntity(movie1.getTitle(), movie1.getDirector(), movie1.getWriter(), movie1.getYear(), movie1.getDuration()));
		movie2 = movieRepository.save(new MovieEntity(movie2.getTitle(), movie2.getDirector(), movie2.getWriter(), movie2.getYear(), movie2.getDuration()));
		movie3 = movieRepository.save(new MovieEntity(movie3.getTitle(), movie3.getDirector(), movie3.getWriter(), movie3.getYear(), movie3.getDuration()));
		movie4 = movieRepository.save(new MovieEntity(movie4.getTitle(), movie4.getDirector(), movie4.getWriter(), movie4.getYear(), movie4.getDuration()));
		movie5 = movieRepository.save(new MovieEntity(movie5.getTitle(), movie5.getDirector(), movie5.getWriter(), movie5.getYear(), movie5.getDuration()));

		review1=reviewRepository.save(new ReviewEntity(review1.getComment(), review1.getScore()));
		review2=reviewRepository.save(new ReviewEntity(review2.getComment(), review2.getScore()));
		review3=reviewRepository.save(new ReviewEntity(review3.getComment(), review3.getScore()));
		review4=reviewRepository.save(new ReviewEntity(review4.getComment(), review4.getScore()));
		review5=reviewRepository.save(new ReviewEntity(review5.getComment(), review5.getScore()));
		review6=reviewRepository.save(new ReviewEntity(review6.getComment(), review6.getScore()));
		review7=reviewRepository.save(new ReviewEntity(review7.getComment(), review7.getScore()));
		review8=reviewRepository.save(new ReviewEntity(review8.getComment(), review8.getScore()));
		review9=reviewRepository.save(new ReviewEntity(review9.getComment(), review9.getScore()));
		review10=reviewRepository.save(new ReviewEntity(review10.getComment(), review10.getScore()));

		if (user1 != null && movie1 != null)
			watchItem1 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie1, user1)));
		if (user1 != null && movie2 != null)
			watchItem2 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie2, user1)));
		if (user1 != null && movie3 != null)
			watchItem3 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie3, user1)));
		if (user2 != null && movie2 != null)
			watchItem4 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie2, user2)));
		if (user2 != null && movie3 != null)
			watchItem5 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie3, user2)));
		if (user3 != null && movie2 != null)
			watchItem6 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie2, user3)));
		if (user3 != null && movie3 != null)
			watchItem7 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie3, user3)));
		if (user3 != null && movie4 != null)
			watchItem8 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie4, user3)));
		if (user4 != null && movie2 != null)
			watchItem9 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie2, user4)));
		if (user4 != null && movie4 != null)
			watchItem10 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie4, user4)));
		if (user5 != null && movie2 != null)
			watchItem11 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie2, user5)));
		if (user5 != null && movie4 != null)
			watchItem12 = watchItemRepository.save(new WatchItemEntity(new WatchItemEntity.CompositeKey(movie4, user5)));

		final WatchItemEntity finalWatchItem1 = watchItem1;
		final WatchItemEntity finalWatchItem2 = watchItem2;
		final WatchItemEntity finalWatchItem3 = watchItem3;
		final WatchItemEntity finalWatchItem4 = watchItem4;
		final WatchItemEntity finalWatchItem5 = watchItem5;
		final WatchItemEntity finalWatchItem6 = watchItem6;
		final WatchItemEntity finalWatchItem7 = watchItem7;
		final WatchItemEntity finalWatchItem8 = watchItem8;
		final WatchItemEntity finalWatchItem9 = watchItem9;
		final WatchItemEntity finalWatchItem10 = watchItem10;
		final WatchItemEntity finalWatchItem11 = watchItem11;
		final WatchItemEntity finalWatchItem12 = watchItem12;

		ArrayList<WatchItemEntity> watchItemEntityArrayListUser1 = new ArrayList<>(){{
			add(finalWatchItem1);
			add(finalWatchItem2);
			add(finalWatchItem3);
		}};
		ArrayList<WatchItemEntity> watchItemEntityArrayListUser2 = new ArrayList<>(){{
			add(finalWatchItem4);
			add(finalWatchItem5);
		}};
		ArrayList<WatchItemEntity> watchItemEntityArrayListUser3 = new ArrayList<>(){{
			add(finalWatchItem6);
			add(finalWatchItem7);
			add(finalWatchItem8);
		}};
		ArrayList<WatchItemEntity> watchItemEntityArrayListUser4 = new ArrayList<>(){{
			add(finalWatchItem9);
			add(finalWatchItem10);
		}};
		ArrayList<WatchItemEntity> watchItemEntityArrayListUser5 = new ArrayList<>(){{
			add(finalWatchItem11);
			add(finalWatchItem12);
		}};

		ArrayList<WatchItemEntity> watchItemEntityArrayListMovie1 = new ArrayList<>(){{
			add(finalWatchItem1);
		}};
		ArrayList<WatchItemEntity> watchItemEntityArrayListMovie2= new ArrayList<>(){{
			add(finalWatchItem2);
			add(finalWatchItem4);
			add(finalWatchItem6);
			add(finalWatchItem9);
			add(finalWatchItem11);
		}};
		ArrayList<WatchItemEntity> watchItemEntityArrayListMovie3 = new ArrayList<>(){{
			add(finalWatchItem3);
			add(finalWatchItem5);
			add(finalWatchItem7);
		}};
		ArrayList<WatchItemEntity> watchItemEntityArrayListMovie4= new ArrayList<>(){{
			add(finalWatchItem8);
			add(finalWatchItem10);
			add(finalWatchItem12);
		}};
		ArrayList<WatchItemEntity> watchItemEntityArrayListMovie5 = new ArrayList<>(){{
		}};

		user1.setWatchItems(watchItemEntityArrayListUser1);
		user2.setWatchItems(watchItemEntityArrayListUser2);
		user3.setWatchItems(watchItemEntityArrayListUser3);
		user4.setWatchItems(watchItemEntityArrayListUser4);
		user5.setWatchItems(watchItemEntityArrayListUser5);

		movie1.setWatchItems(watchItemEntityArrayListMovie1);
		movie2.setWatchItems(watchItemEntityArrayListMovie2);
		movie3.setWatchItems(watchItemEntityArrayListMovie3);
		movie4.setWatchItems(watchItemEntityArrayListMovie4);
		movie5.setWatchItems(watchItemEntityArrayListMovie5);

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);
		userRepository.save(user5);

		movieRepository.save(movie1);
		movieRepository.save(movie2);
		movieRepository.save(movie3);
		movieRepository.save(movie4);
		movieRepository.save(movie5);

		final ReviewEntity finalReview1 = review1;
		final ReviewEntity finalReview2 = review2;
		final ReviewEntity finalReview3 = review3;
		final ReviewEntity finalReview4 = review4;
		final ReviewEntity finalReview5 = review5;
		final ReviewEntity finalReview6 = review6;
		final ReviewEntity finalReview7 = review7;
		final ReviewEntity finalReview8 = review8;
		final ReviewEntity finalReview9 = review9;
		final ReviewEntity finalReview10 = review10;

		ArrayList<ReviewEntity> reviewEntityArrayListMovie1 =new ArrayList<>(){{
			add(finalReview1);
		}};
		ArrayList<ReviewEntity> reviewEntityArrayListMovie2 =new ArrayList<>(){{
			add(finalReview2);
			add(finalReview3);
			add(finalReview9);
			add(finalReview10);
		}};
		ArrayList<ReviewEntity> reviewEntityArrayListMovie3 =new ArrayList<>(){{
			add(finalReview5);
			add(finalReview8);
		}};
		ArrayList<ReviewEntity> reviewEntityArrayListMovie4 =new ArrayList<>(){{
			add(finalReview6);
			add(finalReview7);
			add(finalReview4);
		}};
		ArrayList<ReviewEntity> reviewEntityArrayListMovie5 =new ArrayList<>(){{
		}};

		movie1.setReviews(reviewEntityArrayListMovie1);
		movie2.setReviews(reviewEntityArrayListMovie2);
		movie3.setReviews(reviewEntityArrayListMovie3);
		movie4.setReviews(reviewEntityArrayListMovie4);
		movie5.setReviews(reviewEntityArrayListMovie5);

		review1.setMovie(movie1);
		review2.setMovie(movie2);
		review3.setMovie(movie2);
		review4.setMovie(movie4);
		review5.setMovie(movie4);
		review6.setMovie(movie4);
		review7.setMovie(movie4);
		review8.setMovie(movie3);
		review9.setMovie(movie2);
		review10.setMovie(movie2);

		movieRepository.save(movie1);
		movieRepository.save(movie2);
		movieRepository.save(movie3);
		movieRepository.save(movie4);
		movieRepository.save(movie5);

		review1 = reviewRepository.save(review1);
		review2 = reviewRepository.save(review2);
		review3 = reviewRepository.save(review3);
		review4 = reviewRepository.save(review4);
		review5 = reviewRepository.save(review5);
		review6 = reviewRepository.save(review6);
		review7 = reviewRepository.save(review7);
		review8 = reviewRepository.save(review8);
		review9 = reviewRepository.save(review9);
		review10 = reviewRepository.save(review10);

		final ReviewEntity finalReview11 = review1;
		final ReviewEntity finalReview12 = review2;
		final ReviewEntity finalReview13 = review3;
		final ReviewEntity finalReview14 = review4;
		final ReviewEntity finalReview15 = review5;
		final ReviewEntity finalReview16 = review6;
		final ReviewEntity finalReview17 = review7;
		final ReviewEntity finalReview18 = review8;
		final ReviewEntity finalReview19 = review9;
		final ReviewEntity finalReview20 = review10;

		ArrayList<ReviewEntity> reviewEntityArrayListUser1=new ArrayList<>(){{
			add(finalReview11);
			add(finalReview12);

		}};
		ArrayList<ReviewEntity> reviewEntityArrayListUser2=new ArrayList<>(){{
		}};
		ArrayList<ReviewEntity> reviewEntityArrayListUser3=new ArrayList<>(){{
			add(finalReview13);
			add(finalReview14);
			add(finalReview15);
		}};
		ArrayList<ReviewEntity> reviewEntityArrayListUser4=new ArrayList<>(){{
			add(finalReview16);
			add(finalReview17);
			add(finalReview18);
		}};
		ArrayList<ReviewEntity> reviewEntityArrayListUser5=new ArrayList<>(){{
			add(finalReview19);
			add(finalReview20);
		}};

		user1.setReviews(reviewEntityArrayListUser1);
		user2.setReviews(reviewEntityArrayListUser2);
		user3.setReviews(reviewEntityArrayListUser3);
		user4.setReviews(reviewEntityArrayListUser4);
		user5.setReviews(reviewEntityArrayListUser5);

		review1.setUser(user1);
		review2.setUser(user1);
		review3.setUser(user3);
		review4.setUser(user3);
		review5.setUser(user3);
		review6.setUser(user4);
		review7.setUser(user4);
		review8.setUser(user4);
		review9.setUser(user5);
		review10.setUser(user5);

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);
		userRepository.save(user5);

		reviewRepository.save(review1);
		reviewRepository.save(review2);
		reviewRepository.save(review3);
		reviewRepository.save(review4);
		reviewRepository.save(review5);
		reviewRepository.save(review6);
		reviewRepository.save(review7);
		reviewRepository.save(review8);
		reviewRepository.save(review9);
		reviewRepository.save(review10);

	}

}
