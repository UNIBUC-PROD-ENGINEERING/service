package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.repository.BookingRepository;
import ro.unibuc.hello.repository.UserRepository;
import ro.unibuc.hello.repository.ApartmentRepository;
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

        // Salvează Booking-ul
        return bookingRepository.save(booking);
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
}

