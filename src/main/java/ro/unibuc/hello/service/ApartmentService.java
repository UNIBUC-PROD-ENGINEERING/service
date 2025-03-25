package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.ApartmentEntity;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.repository.ApartmentRepository;
import ro.unibuc.hello.repository.BookingRepository;
import ro.unibuc.hello.repository.ReviewRepository;
import ro.unibuc.hello.repository.UserRepository;  
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.exception.InvalidInputException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.text.Normalizer;
import java.util.HashSet;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;  
    private final ReviewRepository reviewRepository;

    public ApartmentService(ApartmentRepository apartmentRepository, BookingRepository bookingRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.apartmentRepository = apartmentRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;  
        this.reviewRepository = reviewRepository;
    }

    public List<ApartmentEntity> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Optional<ApartmentEntity> getApartmentById(String id) {
        return apartmentRepository.findById(id);
    }

    public ApartmentEntity createApartment(ApartmentEntity apartment) {
        // Verificăm dacă userId există
        Optional<UserEntity> user = userRepository.findById(apartment.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + apartment.getUserId() + " does not exist.");
        }
        return apartmentRepository.save(apartment);
    }

    public void deleteApartment(String id) {
        apartmentRepository.deleteById(id);
    }

    public List<ApartmentEntity> findAvailableApartments(LocalDate startDate, LocalDate endDate) {
        List<ApartmentEntity> allApartments = apartmentRepository.findAll();
        
        List<BookingEntity> overlappingBookings = bookingRepository.findBookedApartmentIds(startDate, endDate);
        Set<String> bookedApartmentIds = overlappingBookings.stream()
                .map(BookingEntity::getApartmentId)
                .collect(Collectors.toSet());
        
        return allApartments.stream()
                .filter(apartment -> !bookedApartmentIds.contains(apartment.getId()))
                .collect(Collectors.toList());
    }
    
    public boolean isApartmentAvailable(String apartmentId, LocalDate startDate, LocalDate endDate) {
        List<BookingEntity> overlappingBookings = bookingRepository.findOverlappingBookings(
                apartmentId, startDate, endDate);
        return overlappingBookings.isEmpty();
    }

    // Funcție existentă: Apartamente pet-friendly
    public List<ApartmentEntity> getPetFriendlyApartments() {
        return apartmentRepository.findByIsPetFriendly(true);
    }

    // Funcție existentă: Apartamente după numărul de camere
    public List<ApartmentEntity> getApartmentsByNumberOfRooms(int numberOfRooms) {
        if (numberOfRooms <= 0) {
            throw new InvalidInputException("Number of rooms must be greater than zero");
        }
        return apartmentRepository.findByNumberOfRooms(numberOfRooms);
    }

    // Funcție existentă: Apartamente după numărul de băi
    public List<ApartmentEntity> getApartmentsByNumberOfBathrooms(int numberOfBathrooms) {
        if (numberOfBathrooms <= 0) {
            throw new InvalidInputException("Number of bathrooms must be greater than zero");
        }
        return apartmentRepository.findByNumberOfBathrooms(numberOfBathrooms);
    }

    // Funcție: Apartamente cu suprafață minimă
    public List<ApartmentEntity> getApartmentsByMinimumSquareMeters(Double minSquareMeters) {
        if (minSquareMeters == null || minSquareMeters < 0) {
            throw new InvalidInputException("Minimum square meters must be a non-negative value");
        }
        return apartmentRepository.findBySquareMetersGreaterThanEqual(minSquareMeters);
    }

    // Funcție: Apartamente sub un anumit preț
    public List<ApartmentEntity> getApartmentsByMaxPrice(Double maxPrice) {
        if (maxPrice == null || maxPrice < 0) {
            throw new InvalidInputException("Maximum price must be a non-negative value");
        }
        return apartmentRepository.findByPricePerNightLessThanEqual(maxPrice);
    }

    // Căutare amenities case-insensitive
    public List<ApartmentEntity> getApartmentsByAmenity(String amenity) {
        if (amenity == null || amenity.trim().isEmpty()) {
            throw new InvalidInputException("Amenity must not be empty");
        }
        return apartmentRepository.findByAmenitiesContainingIgnoreCase(amenity);
    }

    // Funcție: Apartamente unde fumatul este permis/interzis
    public List<ApartmentEntity> getApartmentsBySmokingAllowed(boolean smokingAllowed) {
        return apartmentRepository.findBySmokingAllowed(smokingAllowed);
    }

    // Metoda actualizată: Apartamente după locație (case-insensitive)
    public List<ApartmentEntity> getApartmentsByLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new InvalidInputException("Location must not be empty");
        }
        String normalizedLocation = Normalizer.normalize(location, Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
            .toLowerCase();
        return apartmentRepository.findByLocationIgnoreCase(normalizedLocation);
    }

    // Funcție: Filtru combinat (preț și suprafață)
    public List<ApartmentEntity> getApartmentsByPriceAndSquareMeters(
            Double minPrice, Double maxPrice, Double minSquareMeters, Double maxSquareMeters) {
        if (minPrice == null || minPrice < 0) {
            throw new InvalidInputException("Minimum price must be a non-negative value");
        }
        if (maxPrice == null || maxPrice < 0) {
            throw new InvalidInputException("Maximum price must be a non-negative value");
        }
        if (minPrice > maxPrice) {
            throw new InvalidInputException("Minimum price cannot be greater than maximum price");
        }
        if (minSquareMeters == null || minSquareMeters < 0) {
            throw new InvalidInputException("Minimum square meters must be a non-negative value");
        }
        if (maxSquareMeters == null || maxSquareMeters < 0) {
            throw new InvalidInputException("Maximum square meters must be a non-negative value");
        }
        if (minSquareMeters > maxSquareMeters) {
            throw new InvalidInputException("Minimum square meters cannot be greater than maximum square meters");
        }
        return apartmentRepository.findByPricePerNightBetweenAndSquareMetersBetween(
                minPrice, maxPrice, minSquareMeters, maxSquareMeters);
    }

    public List<ApartmentEntity> searchApartments(
            Double minPrice, Double maxPrice, Double minSquareMeters, Double maxSquareMeters,
            Integer numberOfRooms, Integer numberOfBathrooms, Boolean isPetFriendly, Boolean smokingAllowed,
            String amenity, String location, Double minAverageRating) {

        // Validări
        if (minPrice != null && minPrice < 0) {
            throw new InvalidInputException("Minimum price must be non-negative");
        }
        if (maxPrice != null && maxPrice < 0) {
            throw new InvalidInputException("Maximum price must be non-negative");
        }
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new InvalidInputException("Minimum price cannot be greater than maximum price");
        }
        if (minSquareMeters != null && minSquareMeters < 0) {
            throw new InvalidInputException("Minimum square meters must be non-negative");
        }
        if (maxSquareMeters != null && maxSquareMeters < 0) {
            throw new InvalidInputException("Maximum square meters must be non-negative");
        }
        if (minSquareMeters != null && maxSquareMeters != null && minSquareMeters > maxSquareMeters) {
            throw new InvalidInputException("Minimum square meters cannot be greater than maximum square meters");
        }
        if (numberOfRooms != null && numberOfRooms <= 0) {
            throw new InvalidInputException("Number of rooms must be positive");
        }
        if (numberOfBathrooms != null && numberOfBathrooms <= 0) {
            throw new InvalidInputException("Number of bathrooms must be positive");
        }
        if (amenity != null && amenity.trim().isEmpty()) {
            throw new InvalidInputException("Amenity must not be empty");
        }
        if (location != null && location.trim().isEmpty()) {
            throw new InvalidInputException("Location must not be empty");
        }
        if (minAverageRating != null && (minAverageRating < 1 || minAverageRating > 5)) {
            throw new InvalidInputException("Minimum average rating must be between 1 and 5");
        }

        // Normalizare pentru locație și amenity
        String normalizedLocation = location != null
                ? Normalizer.normalize(location, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .toLowerCase()
                : null;
        String normalizedAmenity = amenity != null
                ? Normalizer.normalize(amenity, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .toLowerCase()
                : null;

        // Pornim cu toate apartamentele
        List<ApartmentEntity> results = apartmentRepository.findAll();

        // Filtru pe rating mediu (doar dacă e specificat)
        if (minAverageRating != null) {
            // Obținem toate review-urile
            List<ReviewEntity> allReviews = reviewRepository.findAll();

            // Calculăm media rating-urilor pentru fiecare apartament
            Map<String, Double> averageRatings = allReviews.stream()
                    .collect(Collectors.groupingBy(
                            ReviewEntity::getApartmentId,
                            Collectors.averagingInt(review -> review.getRating() != null ? review.getRating() : 0)));

            // Aplicăm filtrul pe rating
            results = results.stream()
                    .filter(apartment -> {
                        Double avgRating = averageRatings.getOrDefault(apartment.getId(), 0.0);
                        return avgRating >= minAverageRating;
                    })
                    .collect(Collectors.toList());
        }

        // Aplicăm celelalte filtre progresiv
        if (minPrice != null) {
            results = results.stream()
                    .filter(a -> a.getPricePerNight() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            results = results.stream()
                    .filter(a -> a.getPricePerNight() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (minSquareMeters != null) {
            results = results.stream()
                    .filter(a -> a.getSquareMeters() >= minSquareMeters)
                    .collect(Collectors.toList());
        }
        if (maxSquareMeters != null) {
            results = results.stream()
                    .filter(a -> a.getSquareMeters() <= maxSquareMeters)
                    .collect(Collectors.toList());
        }
        if (numberOfRooms != null) {
            results = results.stream()
                    .filter(a -> a.getNumberOfRooms() == numberOfRooms)
                    .collect(Collectors.toList());
        }
        if (numberOfBathrooms != null) {
            results = results.stream()
                    .filter(a -> a.getNumberOfBathrooms() == numberOfBathrooms)
                    .collect(Collectors.toList());
        }
        if (isPetFriendly != null) {
            results = results.stream()
                    .filter(a -> a.isPetFriendly() == isPetFriendly)
                    .collect(Collectors.toList());
        }
        if (smokingAllowed != null) {
            results = results.stream()
                    .filter(a -> a.isSmokingAllowed() == smokingAllowed)
                    .collect(Collectors.toList());
        }
        if (normalizedAmenity != null) {
            results = results.stream()
                    .filter(a -> a.getAmenities() != null && a.getAmenities().stream()
                            .map(amen -> Normalizer.normalize(amen, Normalizer.Form.NFD)
                                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                                    .toLowerCase())
                            .anyMatch(amen -> amen.contains(normalizedAmenity)))
                    .collect(Collectors.toList());
        }
        if (normalizedLocation != null) {
            results = results.stream()
                    .filter(a -> a.getLocation() != null && Normalizer.normalize(a.getLocation(), Normalizer.Form.NFD)
                            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                            .toLowerCase()
                            .contains(normalizedLocation))
                    .collect(Collectors.toList());
        }

        return results;
    }

    // Metoda nouă pentru filtrare după rating
    public List<ApartmentEntity> getApartmentsByMinAverageRating(Double minAverageRating) {
        // Validare
        if (minAverageRating == null) {
            throw new InvalidInputException("Minimum average rating must be specified");
        }
        if (minAverageRating < 1 || minAverageRating > 5) {
            throw new InvalidInputException("Minimum average rating must be between 1 and 5");
        }

        // Obținem toate apartamentele
        List<ApartmentEntity> allApartments = apartmentRepository.findAll();

        // Obținem toate review-urile
        List<ReviewEntity> allReviews = reviewRepository.findAll();

        // Calculăm media rating-urilor pentru fiecare apartament
        Map<String, Double> averageRatings = allReviews.stream()
                .collect(Collectors.groupingBy(
                        ReviewEntity::getApartmentId,
                        Collectors.averagingInt(review -> review.getRating() != null ? review.getRating() : 0)));

        // Filtrăm apartamentele cu media peste minAverageRating
        return allApartments.stream()
                .filter(apartment -> {
                    Double avgRating = averageRatings.getOrDefault(apartment.getId(), 0.0);
                    return avgRating >= minAverageRating;
                })
                .collect(Collectors.toList());
    }

    
}