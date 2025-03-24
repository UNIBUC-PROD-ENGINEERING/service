package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.unibuc.hello.data.ApartmentEntity;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.exception.InvalidInputException;
import ro.unibuc.hello.repository.ApartmentRepository;
import ro.unibuc.hello.repository.BookingRepository;
import ro.unibuc.hello.repository.ReviewRepository;
import ro.unibuc.hello.repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApartmentServiceTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ApartmentService apartmentService;

    private ApartmentEntity luxuryApartment;
    private ApartmentEntity modernFlat;
    private UserEntity user;
    private ReviewEntity review1;
    private ReviewEntity review2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Date de test
        luxuryApartment = new ApartmentEntity();
        luxuryApartment.setId("67e0582d1bf7c4337e952c36");
        luxuryApartment.setTitle("Luxury Apartment");
        luxuryApartment.setLocation("București");
        luxuryApartment.setPricePerNight(250.0);
        luxuryApartment.setNumberOfRooms(3);
        luxuryApartment.setNumberOfBathrooms(2);
        luxuryApartment.setAmenities(Arrays.asList("Wi-Fi", "TV", "balcon", "aer condiționat"));
        luxuryApartment.setSquareMeters(90.0);
        luxuryApartment.setSmokingAllowed(false);
        luxuryApartment.setPetFriendly(true);
        luxuryApartment.setUserId("user1");

        modernFlat = new ApartmentEntity();
        modernFlat.setId("67e0582d1bf7c4337e952c37");
        modernFlat.setTitle("Modern Flat");
        modernFlat.setLocation("Cluj-Napoca");
        modernFlat.setPricePerNight(180.0);
        modernFlat.setNumberOfRooms(2);
        modernFlat.setNumberOfBathrooms(1);
        modernFlat.setAmenities(Arrays.asList("Wi-Fi", "TV", "mașină de spălat"));
        modernFlat.setSquareMeters(65.5);
        modernFlat.setSmokingAllowed(true);
        modernFlat.setPetFriendly(false);
        modernFlat.setUserId("user1");

        user = new UserEntity();
        user.setId("user1");
        user.setName("testUser"); // Ajustat de la setUsername la setName
        user.setEmail("test@example.com"); // Adăugat pentru consistență

        review1 = new ReviewEntity("Great!", 4, luxuryApartment.getId(), "user1");
        review2 = new ReviewEntity("Amazing!", 5, luxuryApartment.getId(), "user2");
    }

    @Test
    void getAllApartments() {
        List < ApartmentEntity > apartments = Arrays.asList(luxuryApartment, modernFlat);
        when(apartmentRepository.findAll()).thenReturn(apartments);

        List < ApartmentEntity > result = apartmentService.getAllApartments();

        assertEquals(2, result.size());
        assertEquals("Luxury Apartment", result.get(0).getTitle());
        assertEquals("Modern Flat", result.get(1).getTitle());
    }

    @Test
    void getApartmentById_Success() {
        when(apartmentRepository.findById("67e0582d1bf7c4337e952c36")).thenReturn(Optional.of(luxuryApartment));

        Optional < ApartmentEntity > result = apartmentService.getApartmentById("67e0582d1bf7c4337e952c36");

        assertTrue(result.isPresent());
        assertEquals("Luxury Apartment", result.get().getTitle());
    }

    @Test
    void createApartment_Success() {
        when(userRepository.findById("user1")).thenReturn(Optional.of(user));
        when(apartmentRepository.save(luxuryApartment)).thenReturn(luxuryApartment);

        ApartmentEntity result = apartmentService.createApartment(luxuryApartment);

        assertEquals("Luxury Apartment", result.getTitle());
        verify(apartmentRepository, times(1)).save(luxuryApartment);
    }

    @Test
    void createApartment_UserNotFound() {
        when(userRepository.findById("user1")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            apartmentService.createApartment(luxuryApartment);
        });

        assertEquals("User with ID user1 does not exist.", exception.getMessage());
    }

    @Test
    void deleteApartment() {
        doNothing().when(apartmentRepository).deleteById("67e0582d1bf7c4337e952c36");

        apartmentService.deleteApartment("67e0582d1bf7c4337e952c36");

        verify(apartmentRepository, times(1)).deleteById("67e0582d1bf7c4337e952c36");
    }

    @Test
    void findAvailableApartments_SomeAvailable() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        List < BookingEntity > bookings = Collections.singletonList(new BookingEntity(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5), luxuryApartment.getId(), "user1"));
        when(apartmentRepository.findAll()).thenReturn(allApartments);
        when(bookingRepository.findBookedApartmentIds(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5))).thenReturn(bookings);

        List < ApartmentEntity > result = apartmentService.findAvailableApartments(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5));

        assertEquals(1, result.size());
        assertEquals("Modern Flat", result.get(0).getTitle());
    }

    @Test
    void isApartmentAvailable_Available() {
        when(bookingRepository.findOverlappingBookings("67e0582d1bf7c4337e952c36", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5)))
            .thenReturn(Collections.emptyList());

        boolean result = apartmentService.isApartmentAvailable("67e0582d1bf7c4337e952c36", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5));

        assertTrue(result);
    }

    @Test
    void isApartmentAvailable_NotAvailable() {
        List < BookingEntity > bookings = Collections.singletonList(new BookingEntity(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5), luxuryApartment.getId(), "user1"));
        when(bookingRepository.findOverlappingBookings("67e0582d1bf7c4337e952c36", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5)))
            .thenReturn(bookings);

        boolean result = apartmentService.isApartmentAvailable("67e0582d1bf7c4337e952c36", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5));

        assertFalse(result);
    }

    @Test
    void getPetFriendlyApartments() {
        List < ApartmentEntity > petFriendly = Collections.singletonList(luxuryApartment);
        when(apartmentRepository.findByIsPetFriendly(true)).thenReturn(petFriendly);

        List < ApartmentEntity > result = apartmentService.getPetFriendlyApartments();

        assertEquals(1, result.size());
        assertTrue(result.get(0).isPetFriendly());
    }

    @Test
    void getApartmentsByNumberOfRooms_InvalidInput() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByNumberOfRooms(0);
        });

        assertEquals("Number of rooms must be greater than zero", exception.getMessage());
    }

    @Test
    void getApartmentsByNumberOfRooms_Success() {
        List < ApartmentEntity > rooms = Collections.singletonList(modernFlat);
        when(apartmentRepository.findByNumberOfRooms(2)).thenReturn(rooms);

        List < ApartmentEntity > result = apartmentService.getApartmentsByNumberOfRooms(2);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getNumberOfRooms());
    }

    @Test
    void getApartmentsByMinAverageRating_Success() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        List < ReviewEntity > reviews = Arrays.asList(review1, review2, new ReviewEntity("OK", 3, modernFlat.getId(), "user1"));
        when(apartmentRepository.findAll()).thenReturn(allApartments);
        when(reviewRepository.findAll()).thenReturn(reviews);

        List < ApartmentEntity > result = apartmentService.getApartmentsByMinAverageRating(4.0);

        assertEquals(1, result.size());
        assertEquals("Luxury Apartment", result.get(0).getTitle()); // Media 4.5 > 4
    }

    @Test
    void searchApartments_CombinedFilters() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        List < ReviewEntity > reviews = Arrays.asList(review1, review2, new ReviewEntity("OK", 3, modernFlat.getId(), "user1"));
        when(apartmentRepository.findAll()).thenReturn(allApartments);
        when(reviewRepository.findAll()).thenReturn(reviews);

        List < ApartmentEntity > result = apartmentService.searchApartments(
            null, 200.0, null, null, 2, null, null, true, "Wi-Fi", null, null);

        assertEquals(1, result.size());
        assertEquals("Modern Flat", result.get(0).getTitle());
    }

    @Test
    void searchApartments_InvalidRating() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.searchApartments(null, null, null, null, null, null, null, null, null, null, 6.0);
        });

        assertEquals("Minimum average rating must be between 1 and 5", exception.getMessage());
    }

    @Test
    void searchApartments_ByLocationWithDiacritics() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        when(apartmentRepository.findAll()).thenReturn(allApartments);

        List < ApartmentEntity > result = apartmentService.searchApartments(
            null, null, null, null, null, null, null, null, null, "Bucuresti", null);

        assertEquals(1, result.size());
        assertEquals("București", result.get(0).getLocation());
    }
    @Test
    void getApartmentsByNumberOfBathrooms_InvalidInput() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByNumberOfBathrooms(0);
        });

        assertEquals("Number of bathrooms must be greater than zero", exception.getMessage());
    }

    @Test
    void getApartmentsByNumberOfBathrooms_Success() {
        List < ApartmentEntity > bathrooms = Collections.singletonList(luxuryApartment);
        when(apartmentRepository.findByNumberOfBathrooms(2)).thenReturn(bathrooms);

        List < ApartmentEntity > result = apartmentService.getApartmentsByNumberOfBathrooms(2);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getNumberOfBathrooms());
    }

    @Test
    void getApartmentsByMinimumSquareMeters_InvalidInput() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByMinimumSquareMeters(-1.0);
        });

        assertEquals("Minimum square meters must be a non-negative value", exception.getMessage());
    }

    @Test
    void getApartmentsByMinimumSquareMeters_Success() {
        List < ApartmentEntity > squareMeters = Collections.singletonList(luxuryApartment);
        when(apartmentRepository.findBySquareMetersGreaterThanEqual(80.0)).thenReturn(squareMeters);

        List < ApartmentEntity > result = apartmentService.getApartmentsByMinimumSquareMeters(80.0);

        assertEquals(1, result.size());
        assertEquals(90.0, result.get(0).getSquareMeters());
    }

    @Test
    void getApartmentsByMaxPrice_InvalidInput() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByMaxPrice(-10.0);
        });

        assertEquals("Maximum price must be a non-negative value", exception.getMessage());
    }

    @Test
    void getApartmentsByMaxPrice_Success() {
        List < ApartmentEntity > prices = Collections.singletonList(modernFlat);
        when(apartmentRepository.findByPricePerNightLessThanEqual(200.0)).thenReturn(prices);

        List < ApartmentEntity > result = apartmentService.getApartmentsByMaxPrice(200.0);

        assertEquals(1, result.size());
        assertEquals(180.0, result.get(0).getPricePerNight());
    }
    // Teste pentru cazuri de margine
    @Test
    void getAllApartments_EmptyList() {
        when(apartmentRepository.findAll()).thenReturn(Collections.emptyList());

        List < ApartmentEntity > result = apartmentService.getAllApartments();

        assertTrue(result.isEmpty());
    }

    @Test
    void getApartmentById_NotFound() {
        when(apartmentRepository.findById("nonexistent")).thenReturn(Optional.empty());

        Optional < ApartmentEntity > result = apartmentService.getApartmentById("nonexistent");

        assertFalse(result.isPresent());
    }

    @Test
    void getApartmentsByMinAverageRating_InvalidInputNull() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByMinAverageRating(null);
        });

        assertEquals("Minimum average rating must be specified", exception.getMessage());
    }

    @Test
    void getApartmentsByMinAverageRating_NoReviews() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        when(apartmentRepository.findAll()).thenReturn(allApartments);
        when(reviewRepository.findAll()).thenReturn(Collections.emptyList());

        List < ApartmentEntity > result = apartmentService.getApartmentsByMinAverageRating(4.0);

        assertTrue(result.isEmpty()); // Niciun apartament nu are rating
    }
    // Teste suplimentare pentru searchApartments
    @Test
    void searchApartments_InvalidMinPrice() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.searchApartments(-1.0, null, null, null, null, null, null, null, null, null, null);
        });

        assertEquals("Minimum price must be non-negative", exception.getMessage());
    }

    @Test
    void searchApartments_InvalidMinMaxSquareMeters() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.searchApartments(null, null, 80.0, 50.0, null, null, null, null, null, null, null);
        });

        assertEquals("Minimum square meters cannot be greater than maximum square meters", exception.getMessage());
    }

    @Test
    void searchApartments_FilterByBathrooms() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        when(apartmentRepository.findAll()).thenReturn(allApartments);

        List < ApartmentEntity > result = apartmentService.searchApartments(
            null, null, null, null, null, 2, null, null, null, null, null);

        assertEquals(1, result.size());
        assertEquals("Luxury Apartment", result.get(0).getTitle());
    }

    @Test
    void searchApartments_FilterByMultipleCriteria() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        when(apartmentRepository.findAll()).thenReturn(allApartments);

        List < ApartmentEntity > result = apartmentService.searchApartments(
            200.0, 300.0, 80.0, null, 3, null, true, false, "balcon", null, null);

        assertEquals(1, result.size());
        assertEquals("Luxury Apartment", result.get(0).getTitle());
    }

    @Test
    void searchApartments_NoResults() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        when(apartmentRepository.findAll()).thenReturn(allApartments);

        List < ApartmentEntity > result = apartmentService.searchApartments(
            null, null, null, null, 4, null, null, null, null, null, null); // Niciun apartament cu 4 camere

        assertEquals(0, result.size());
    }
    // ... (codul existent rămâne neschimbat)

    // Teste suplimentare pentru getApartmentsByPriceAndSquareMeters
    @Test
    void getApartmentsByPriceAndSquareMeters_InvalidMinSquareMeters() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByPriceAndSquareMeters(100.0, 200.0, -10.0, 80.0);
        });

        assertEquals("Minimum square meters must be a non-negative value", exception.getMessage());
    }

    @Test
    void getApartmentsByPriceAndSquareMeters_InvalidMinMaxSquareMeters() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByPriceAndSquareMeters(100.0, 200.0, 80.0, 50.0);
        });

        assertEquals("Minimum square meters cannot be greater than maximum square meters", exception.getMessage());
    }

    // Teste pentru lambda$searchApartments$2 (rating null)
    @Test
    void searchApartments_WithMinAverageRating_NullRatings() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        List < ReviewEntity > reviews = Arrays.asList(
            new ReviewEntity("Great!", null, luxuryApartment.getId(), "user1"),
            new ReviewEntity("OK", 3, modernFlat.getId(), "user1")
        );
        when(apartmentRepository.findAll()).thenReturn(allApartments);
        when(reviewRepository.findAll()).thenReturn(reviews);

        List < ApartmentEntity > result = apartmentService.searchApartments(
            null, null, null, null, null, null, null, null, null, null, 4.0);

        assertTrue(result.isEmpty()); // Niciun apartament nu are rating >= 4
    }

    
    @Test
    void searchApartments_FilterByMaxSquareMeters() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        when(apartmentRepository.findAll()).thenReturn(allApartments);

        List < ApartmentEntity > result = apartmentService.searchApartments(
            null, null, null, 70.0, null, null, null, null, null, null, null);

        assertEquals(1, result.size());
        assertEquals("Modern Flat", result.get(0).getTitle()); // 65.5 <= 70
    }

    @Test
    void getApartmentsByMinAverageRating_NullRatings() {
        List < ApartmentEntity > allApartments = Arrays.asList(luxuryApartment, modernFlat);
        List < ReviewEntity > reviews = Arrays.asList(
            new ReviewEntity("Great!", null, luxuryApartment.getId(), "user1"),
            new ReviewEntity("OK", 3, modernFlat.getId(), "user1")
        );
        when(apartmentRepository.findAll()).thenReturn(allApartments);
        when(reviewRepository.findAll()).thenReturn(reviews);

        List < ApartmentEntity > result = apartmentService.getApartmentsByMinAverageRating(4.0);

        assertTrue(result.isEmpty()); // Niciun apartament nu are rating >= 4
    }

   
    @Test
    void getApartmentsBySmokingAllowed_SuccessFalse() {
        List<ApartmentEntity> nonSmoking = Collections.singletonList(luxuryApartment);
        when(apartmentRepository.findBySmokingAllowed(false)).thenReturn(nonSmoking);

        List<ApartmentEntity> result = apartmentService.getApartmentsBySmokingAllowed(false);

        assertEquals(1, result.size());
        assertFalse(result.get(0).isSmokingAllowed());
    }

    @Test
    void getApartmentsByLocation_SuccessWithDiacritics() {
        List<ApartmentEntity> locations = Collections.singletonList(luxuryApartment);
        // După normalizare, "București" devine "bucuresti"
        when(apartmentRepository.findByLocationIgnoreCase("bucuresti")).thenReturn(locations);

        List<ApartmentEntity> result = apartmentService.getApartmentsByLocation("București");

        assertEquals(1, result.size());
        assertEquals("București", result.get(0).getLocation());
    }

    @Test
    void getApartmentsByLocation_SuccessNoDiacritics() {
        List<ApartmentEntity> locations = Collections.singletonList(luxuryApartment);
        // Fără diacritice, tot "bucuresti"
        when(apartmentRepository.findByLocationIgnoreCase("bucuresti")).thenReturn(locations);

        List<ApartmentEntity> result = apartmentService.getApartmentsByLocation("Bucuresti");

        assertEquals(1, result.size());
        assertEquals("București", result.get(0).getLocation());
    }

    @Test
    void getApartmentsByLocation_SuccessCaseInsensitive() {
        List<ApartmentEntity> locations = Collections.singletonList(luxuryApartment);
        // Testăm cu uppercase, dar normalizarea face "BUCURESTI" -> "bucuresti"
        when(apartmentRepository.findByLocationIgnoreCase("bucuresti")).thenReturn(locations);

        List<ApartmentEntity> result = apartmentService.getApartmentsByLocation("BUCUREȘTI");

        assertEquals(1, result.size());
        assertEquals("București", result.get(0).getLocation());
    }

    @Test
    void getApartmentsByLocation_InvalidInputEmpty() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByLocation("");
        });

        assertEquals("Location must not be empty", exception.getMessage());
    }

    @Test
    void getApartmentsByLocation_InvalidInputNull() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByLocation(null);
        });

        assertEquals("Location must not be empty", exception.getMessage());
    }
    /*
    @Test
    void getApartmentsByAmenity_SuccessCaseInsensitive() {
        List<ApartmentEntity> allApartments = Arrays.asList(luxuryApartment, modernFlat);
        // Mock-uim findByAmenitiesContainingIgnoreCase
        when(apartmentRepository.findByAmenitiesContainingIgnoreCase("wi-fi")).thenReturn(allApartments);

        List<ApartmentEntity> result = apartmentService.getApartmentsByAmenity("wi-fi");

        assertEquals(2, result.size());
        assertTrue(result.get(0).getAmenities().contains("Wi-Fi"));
    }

    @Test
    void getApartmentsByAmenity_SuccessUpperCase() {
        List<ApartmentEntity> allApartments = Arrays.asList(luxuryApartment, modernFlat);
        // Testăm cu uppercase, dar metoda e case-insensitive
        when(apartmentRepository.findByAmenitiesContainingIgnoreCase("WI-FI")).thenReturn(allApartments);

        List<ApartmentEntity> result = apartmentService.getApartmentsByAmenity("WI-FI");

        assertEquals(2, result.size());
        assertTrue(result.get(0).getAmenities().contains("Wi-Fi"));
    }
    */
    @Test
    void getApartmentsByAmenity_InvalidInputEmpty() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByAmenity("");
        });

        assertEquals("Amenity must not be empty", exception.getMessage());
    }

    @Test
    void getApartmentsByAmenity_InvalidInputNull() {
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            apartmentService.getApartmentsByAmenity(null);
        });

        assertEquals("Amenity must not be empty", exception.getMessage());
    }

}