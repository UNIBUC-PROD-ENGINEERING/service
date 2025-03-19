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
import java.util.stream.Collectors;

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
        if (!apartmentRepository.existsById(booking.getApartmentId())) {
            throw new IllegalArgumentException("Apartment with ID " + booking.getApartmentId() + " does not exist.");
        }

        if (!userRepository.existsById(booking.getUserId())) {
            throw new IllegalArgumentException("User with ID " + booking.getUserId() + " does not exist.");
        }

        List<BookingEntity> allBookings = bookingRepository.findAll();
        boolean isAvailable = isApartmentAvailableJava(
                allBookings, 
                booking.getApartmentId(),
                booking.getStartDate(), 
                booking.getEndDate()
        );

        if (!isAvailable) {
            throw new IllegalArgumentException("Apartment is not available for the selected dates.");
        }

        return bookingRepository.save(booking);
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
    
    public boolean isApartmentAvailable(String apartmentId, LocalDate startDate, LocalDate endDate) {
        if (!apartmentRepository.existsById(apartmentId)) {
            throw new IllegalArgumentException("Apartment with ID " + apartmentId + " does not exist.");
        }
        
        List<BookingEntity> allBookings = bookingRepository.findAll();
        return isApartmentAvailableJava(allBookings, apartmentId, startDate, endDate);
    }
    
    public List<BookingEntity> getBookingsForApartment(String apartmentId) {
        return bookingRepository.findByApartmentId(apartmentId);
    }
    
    public List<BookingEntity> getBookingsForApartmentAndUser(String apartmentId, String userId) {
        return bookingRepository.findByApartmentIdAndUserId(apartmentId, userId);
    }
    
    public List<BookingEntity> findOverlappingBookingsJava(
            List<BookingEntity> bookings,
            String apartmentId,
            LocalDate startDate,
            LocalDate endDate) {
        
        return bookings.stream()
                .filter(booking -> booking.getApartmentId().equals(apartmentId))
                .filter(booking -> {
                    boolean condition1 = !booking.getStartDate().isAfter(endDate) && 
                                         !booking.getEndDate().isBefore(startDate);
                    
                    boolean condition2 = !booking.getStartDate().isAfter(endDate) && 
                                         !booking.getStartDate().isBefore(startDate);
                    
                    return condition1 || condition2;
                })
                .collect(Collectors.toList());
    }
    
    public boolean isApartmentAvailableJava(
            List<BookingEntity> bookings,
            String apartmentId,
            LocalDate startDate,
            LocalDate endDate) {
        
        List<BookingEntity> overlappingBookings = findOverlappingBookingsJava(
                bookings, apartmentId, startDate, endDate);
        
        return overlappingBookings.isEmpty();
    }
    
    public List<String> findBookedApartmentIdsJava(
            List<BookingEntity> bookings,
            LocalDate startDate,
            LocalDate endDate) {
        
        return bookings.stream()
                .filter(booking -> {
                    boolean condition1 = !booking.getStartDate().isAfter(endDate) && 
                                         !booking.getEndDate().isBefore(startDate);
                    
                    boolean condition2 = !booking.getStartDate().isAfter(endDate) && 
                                         !booking.getStartDate().isBefore(startDate);
                    
                    return condition1 || condition2;
                })
                .map(BookingEntity::getApartmentId)
                .distinct()
                .collect(Collectors.toList());
    }
        
    public List<BookingEntity> findOverlappingBookingsForTesting(String apartmentId, LocalDate startDate, LocalDate endDate) {
        List<BookingEntity> allBookings = bookingRepository.findAll();
        return findOverlappingBookingsJava(allBookings, apartmentId, startDate, endDate);
    }
    
    public boolean isApartmentAvailableForTesting(String apartmentId, LocalDate startDate, LocalDate endDate) {
        if (!apartmentRepository.existsById(apartmentId)) {
            throw new IllegalArgumentException("Apartment with ID " + apartmentId + " does not exist.");
        }
        
        List<BookingEntity> allBookings = bookingRepository.findAll();
        return isApartmentAvailableJava(allBookings, apartmentId, startDate, endDate);
    }
    
    public List<String> findBookedApartmentIdsForTesting(LocalDate startDate, LocalDate endDate) {
        List<BookingEntity> allBookings = bookingRepository.findAll();
        return findBookedApartmentIdsJava(allBookings, startDate, endDate);
    }
    
    public List<String> findAvailableApartmentIds(LocalDate startDate, LocalDate endDate) {
        List<BookingEntity> allBookings = bookingRepository.findAll();
        List<String> bookedApartmentIds = findBookedApartmentIdsJava(allBookings, startDate, endDate);
        
        List<String> allApartmentIds = apartmentRepository.findAll().stream()
                .map(apartment -> apartment.getId())
                .collect(Collectors.toList());
        
        allApartmentIds.removeAll(bookedApartmentIds);
        
        return allApartmentIds;
    }
}