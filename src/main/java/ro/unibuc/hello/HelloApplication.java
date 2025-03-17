package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.repository.*;
import java.time.LocalDate;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Arrays;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "ro.unibuc.hello.repository")
public class HelloApplication {

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    @PostConstruct
    public void runAfterObjectCreated() {
        // Șterge toate datele existente
        informationRepository.deleteAll();
        apartmentRepository.deleteAll();
        userRepository.deleteAll();
        bookingRepository.deleteAll();
        reviewRepository.deleteAll();

        // Adaugă informații de bază
        informationRepository.save(new InformationEntity("Overview",
                "This is an example of using a data storage engine running separately from our applications server"));

        // Adaugă un utilizator de test
        UserEntity user = new UserEntity("John Doe", "john.doe@example.com");
        userRepository.save(user);

        // Adaugă un apartament de test
        ApartmentEntity apartment = new ApartmentEntity(
            "Luxury Apartment", 
            "București", 
            250.0, 
            user.getId(), 
            3, 
            2, 
            true, 
            Arrays.asList("Wi-Fi", "TV", "balcon", "aer condiționat"), 
            90.0, 
            false
        );
        apartmentRepository.save(apartment);

        ApartmentEntity apartment2 = new ApartmentEntity(
            "Modern Flat", 
            "Cluj-Napoca", 
            180.0, 
            user.getId(), 
            2, 
            1, 
            false, 
            Arrays.asList("Wi-Fi", "TV", "mașină de spălat"), 
            65.5, 
            true
        );
        apartmentRepository.save(apartment2);
        // Adaugă o rezervare de test (apel corect al constructorului)
        BookingEntity booking = new BookingEntity(
                LocalDate.parse("2025-04-01"),  // Start date
                LocalDate.parse("2025-04-10"),  // End date
                apartment.getId(),  // Apartment ID
                user.getId()  // Renter ID
        );
        bookingRepository.save(booking);

        // Adaugă o recenzie de test (apel corect al constructorului)
        ReviewEntity review = new ReviewEntity(
                "Amazing place!",  // Comment
                5,  // Rating
                apartment.getId(),  // Apartment ID
                user.getId()  // User ID
        );
        reviewRepository.save(review);
    }
}