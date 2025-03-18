package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.ApartmentEntity;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.repository.ApartmentRepository;
import ro.unibuc.hello.repository.BookingRepository;
import ro.unibuc.hello.repository.UserRepository;  
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.exception.InvalidInputException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;  


    public ApartmentService(ApartmentRepository apartmentRepository, BookingRepository bookingRepository, UserRepository userRepository) {
        this.apartmentRepository = apartmentRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;  
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
        return apartmentRepository.findByLocationIgnoreCase(location);
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
}