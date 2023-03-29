package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.*;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = InformationRepository.class)
public class HelloApplication {

	@Autowired
	private InformationRepository informationRepository;
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private TicketRespository ticketRespository;

	@Autowired
	private CinemaRoomRepository cinemaRoomRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private VipLoungeRepository vipLoungeRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {

		MovieEntity movie1 = new MovieEntity("Avatar",
				"Jake Sully lives with his newfound family formed on the extrasolar moon Pandora. Once a familiar threat returns to finish what was previously started, Jake must work with Neytiri and the army of the Na'vi race to protect their home.",
				192);
		MovieEntity movie2 = new MovieEntity("Ford v Ferrari",
				"American car designer Carroll Shelby and driver Ken Miles battle corporate interference and the laws of physics to build a revolutionary race car for Ford in order to defeat Ferrari at the 24 Hours of Le Mans in 1966",
				152);
		MovieEntity movie3 = new MovieEntity("Titanic",
				"A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
				196);

		movieRepository.deleteAll();
		movieRepository.save(movie1);
		movieRepository.save(movie2);
		movieRepository.save(movie3);
		movieRepository.save(new MovieEntity("Dunkirk",
				"Allied soldiers from Belgium, the British Commonwealth and Empire, and France are surrounded by the German Army and evacuated during a fierce battle in World War II.",
				106));
		movieRepository.save(new MovieEntity("Fight Club",
				"An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.",
				139));
		movieRepository.save(new MovieEntity("Bullet Train",
				"Five assassins aboard a swiftly-moving bullet train find out that their missions have something in common."
				,126));


		ticketRespository.deleteAll();
		ticketRespository.save(new TicketEntity(movie1, 10,10,2023, 19,20));
		ticketRespository.save(new TicketEntity(movie2, 20, 4, 2023, 18,30));
		ticketRespository.save(new TicketEntity(movie3, 3,5,2023,15,20));

			
		LocationEntity location1 = (new LocationEntity("Bulevardul General Paul Teodorescu 4, București 061344",
				"Afi Cotroceni Cinema City", "037 283 9065"));
		LocationEntity location2 = (new LocationEntity("Bulevardul Pierre de Coubertin 3-5, București 021901",
				"Mega Mall Cinema City", "037 283 9066"));
		LocationEntity location3 = (new LocationEntity("Calea Moșilor 127, București 020854",
				"Cinema Europa", "021 367 2567"));
		LocationEntity location4 = (new LocationEntity("Liberty Center, Etaj 2, Strada Progresului 151-171, București 050696",
				"Happy Cinema", "031 426 0536"));
		LocationEntity location5 = (new LocationEntity("Strada Ion Câmpineanu 21, București 010033",
				"Cinema Union", "021 313 9289"));
		LocationEntity location6 = (new LocationEntity("București Mall, Etaj 2, Calea Vitan 55-59, București 031281",
				"Hollywood Multiplex", "021 327 7020"));

		locationRepository.deleteAll();
		locationRepository.save(location1);
		locationRepository.save(location2);
		locationRepository.save(location3);
		locationRepository.save(location4);
		locationRepository.save(location5);
		locationRepository.save(location6);

		cinemaRoomRepository.deleteAll();
		cinemaRoomRepository.save(new CinemaRoomEntity(location1, 10));
		cinemaRoomRepository.save(new CinemaRoomEntity(location3, 5));
		cinemaRoomRepository.save(new CinemaRoomEntity(location6, 2));

		TicketEntity ticket1 = new TicketEntity(movie1, 10,10,2023, 19,20);
		TicketEntity ticket2 = new TicketEntity(movie2, 20, 4, 2023, 18,30);
		TicketEntity ticket3 = new TicketEntity(movie3, 3,5,2023,15,20);

		ticketRespository.deleteAll();
		ticketRespository.save(ticket1);
		ticketRespository.save(ticket2);
		ticketRespository.save(ticket3);

		customerRepository.deleteAll();
		customerRepository.save(new CustomerEntity("Andrei", 20));
		customerRepository.save(new CustomerEntity("Luca", 22));
		customerRepository.save(new CustomerEntity("Petru", 19));
		customerRepository.save(new CustomerEntity("Gigi", 25));

		vipLoungeRepository.deleteAll();
		vipLoungeRepository.save(new VipLoungeEntity("150lei", location2));
		vipLoungeRepository.save(new VipLoungeEntity("200lei", location3));
		vipLoungeRepository.save(new VipLoungeEntity("130lei", location6));
		vipLoungeRepository.save(new VipLoungeEntity("150lei", location4));
		vipLoungeRepository.save(new VipLoungeEntity("220lei", location2));
		vipLoungeRepository.save(new VipLoungeEntity("140lei", location5));

		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));
	}

}
