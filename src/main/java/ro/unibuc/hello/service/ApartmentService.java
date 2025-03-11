package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.ApartmentEntity;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.repository.ApartmentRepository;
import ro.unibuc.hello.repository.BookingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final BookingRepository bookingRepository;


    public ApartmentService(ApartmentRepository apartmentRepository, BookingRepository bookingRepository) {
        this.apartmentRepository = apartmentRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<ApartmentEntity> getAllApartments() {
        return apartmentRepository.findAll();
    }

    public Optional<ApartmentEntity> getApartmentById(String id) {
        return apartmentRepository.findById(id);
    }

    public ApartmentEntity createApartment(ApartmentEntity apartment) {
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
}
