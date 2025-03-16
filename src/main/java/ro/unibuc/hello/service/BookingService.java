package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.repository.BookingRepository;
import ro.unibuc.hello.repository.UserRepository;
import ro.unibuc.hello.repository.ApartmentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;


    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, ApartmentRepository apartmentRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.apartmentRepository = apartmentRepository;
    }

    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<BookingEntity> getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    public BookingEntity createBooking(BookingEntity booking) {
        // Verifică dacă apartamentul există
        if (!apartmentRepository.existsById(booking.getApartmentId())) {
            throw new IllegalArgumentException("Apartment with ID " + booking.getApartmentId() + " does not exist.");
        }

        // Verifică dacă utilizatorul există
        if (!userRepository.existsById(booking.getUserId())) {
            throw new IllegalArgumentException("User with ID " + booking.getUserId() + " does not exist.");
        }

        List<BookingEntity> overlappingBookings = bookingRepository.findOverlappingBookings(
                booking.getApartmentId(),
                booking.getStartDate(),
                booking.getEndDate()
        );

        if (!overlappingBookings.isEmpty()) {
            throw new IllegalArgumentException("Apartment is not available for the selected dates.");
        }

        return bookingRepository.save(booking);
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
    
    public boolean isApartmentAvailable(String apartmentId, LocalDate startDate, LocalDate endDate) {
        // Verifică dacă apartamentul există
        if (!apartmentRepository.existsById(apartmentId)) {
            throw new IllegalArgumentException("Apartment with ID " + apartmentId + " does not exist.");
        }
        
        List<BookingEntity> overlappingBookings = bookingRepository.findOverlappingBookings(
                apartmentId, startDate, endDate);
        return overlappingBookings.isEmpty();
    }
    
    public List<BookingEntity> getBookingsForApartment(String apartmentId) {
        return bookingRepository.findByApartmentId(apartmentId);
    }
    
    public List<BookingEntity> getBookingsForApartmentAndUser(String apartmentId, String userId) {
        return bookingRepository.findByApartmentIdAndUserId(apartmentId, userId);
    }
}

