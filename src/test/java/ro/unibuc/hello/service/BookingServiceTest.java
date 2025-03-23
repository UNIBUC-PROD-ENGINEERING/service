package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.ApartmentEntity;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.repository.ApartmentRepository;
import ro.unibuc.hello.repository.BookingRepository;
import ro.unibuc.hello.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApartmentRepository apartmentRepository;

    @InjectMocks
    private BookingService bookingService;

    private BookingEntity booking1;
    private BookingEntity booking2;
    private final String apartmentId1 = "apartment1";
    private final String apartmentId2 = "apartment2";
    private final String userId1 = "user1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        booking1 = new BookingEntity(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 5),
                apartmentId1,
                userId1
        );
        booking1.setId("booking1");

        booking2 = new BookingEntity(
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 15),
                apartmentId2,
                userId1
        );
        booking2.setId("booking2");
    }

    @Test
    void getAllBookings() {
        List<BookingEntity> bookings = Arrays.asList(booking1, booking2);
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<BookingEntity> result = bookingService.getAllBookings();

        assertEquals(2, result.size());
        assertEquals("booking1", result.get(0).getId());
        assertEquals("booking2", result.get(1).getId());
    }

    @Test
    void getBookingById() {
        when(bookingRepository.findById("booking1")).thenReturn(Optional.of(booking1));

        Optional<BookingEntity> result = bookingService.getBookingById("booking1");

        assertTrue(result.isPresent());
        assertEquals("booking1", result.get().getId());
    }

    @Test
    void testCreateBooking_Success() {
        BookingEntity newBooking = new BookingEntity(
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 2, 5),
                apartmentId1,
                userId1
        );

        when(apartmentRepository.existsById(apartmentId1)).thenReturn(true);
        when(userRepository.existsById(userId1)).thenReturn(true);
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());
        when(bookingRepository.save(any(BookingEntity.class))).thenReturn(newBooking);

        BookingEntity result = bookingService.createBooking(newBooking);

        assertNotNull(result);
        assertEquals(apartmentId1, result.getApartmentId());
        assertEquals(userId1, result.getUserId());
    }

    @Test
    void testCreateBooking_ApartmentNotFound() {
        BookingEntity newBooking = new BookingEntity(
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 2, 5),
                "nonexistentApartment",
                userId1
        );

        when(apartmentRepository.existsById("nonexistentApartment")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookingService.createBooking(newBooking)
        );
        assertTrue(exception.getMessage().contains("does not exist"));
    }

    @Test
    void testCreateBooking_UserNotFound() {
        BookingEntity newBooking = new BookingEntity(
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2025, 2, 5),
                apartmentId1,
                "nonexistentUser"
        );

        when(apartmentRepository.existsById(apartmentId1)).thenReturn(true);
        when(userRepository.existsById("nonexistentUser")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookingService.createBooking(newBooking)
        );
        assertTrue(exception.getMessage().contains("does not exist"));
    }

    @Test
    void testCreateBooking_ApartmentNotAvailable() {
        BookingEntity newBooking = new BookingEntity(
                LocalDate.of(2025, 1, 3),
                LocalDate.of(2025, 1, 8),
                apartmentId1,
                userId1
        );

        List<BookingEntity> existingBookings = Arrays.asList(booking1);

        when(apartmentRepository.existsById(apartmentId1)).thenReturn(true);
        when(userRepository.existsById(userId1)).thenReturn(true);
        when(bookingRepository.findAll()).thenReturn(existingBookings);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookingService.createBooking(newBooking)
        );
        assertTrue(exception.getMessage().contains("not available"));
    }

    @Test
    void testDeleteBooking() {
        bookingService.deleteBooking("booking1");

        verify(bookingRepository, times(1)).deleteById("booking1");
    }

    @Test
    void testIsApartmentAvailable_Available() {
        LocalDate startDate = LocalDate.of(2025, 2, 1);
        LocalDate endDate = LocalDate.of(2025, 2, 5);

        when(apartmentRepository.existsById(apartmentId1)).thenReturn(true);
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        boolean result = bookingService.isApartmentAvailable(apartmentId1, startDate, endDate);

        assertTrue(result);
    }

    @Test
    void testIsApartmentAvailable_NotAvailable() {
        LocalDate startDate = LocalDate.of(2025, 1, 3);
        LocalDate endDate = LocalDate.of(2025, 1, 8);

        when(apartmentRepository.existsById(apartmentId1)).thenReturn(true);
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        boolean result = bookingService.isApartmentAvailable(apartmentId1, startDate, endDate);

        assertFalse(result);
    }

    @Test
    void testIsApartmentAvailable_ApartmentNotFound() {
        LocalDate startDate = LocalDate.of(2025, 2, 1);
        LocalDate endDate = LocalDate.of(2025, 2, 5);

        when(apartmentRepository.existsById("nonexistentApartment")).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookingService.isApartmentAvailable("nonexistentApartment", startDate, endDate)
        );
        assertTrue(exception.getMessage().contains("does not exist"));
    }

    @Test
    void testGetBookingsForApartment() {
        when(bookingRepository.findByApartmentId(apartmentId1)).thenReturn(Arrays.asList(booking1));

        List<BookingEntity> result = bookingService.getBookingsForApartment(apartmentId1);

        assertEquals(1, result.size());
        assertEquals(apartmentId1, result.get(0).getApartmentId());
    }

    @Test
    void testGetBookingsForApartmentAndUser() {
        when(bookingRepository.findByApartmentIdAndUserId(apartmentId1, userId1))
                .thenReturn(Arrays.asList(booking1));

        List<BookingEntity> result = bookingService.getBookingsForApartmentAndUser(apartmentId1, userId1);

        assertEquals(1, result.size());
        assertEquals(apartmentId1, result.get(0).getApartmentId());
        assertEquals(userId1, result.get(0).getUserId());
    }

    @Test
    void testFindOverlappingBookingsJava_OverlappingExists() {
        LocalDate startDate = LocalDate.of(2025, 1, 3);
        LocalDate endDate = LocalDate.of(2025, 1, 8);
        List<BookingEntity> bookings = Arrays.asList(booking1, booking2);

        List<BookingEntity> result = bookingService.findOverlappingBookingsJava(bookings, apartmentId1, startDate, endDate);

        assertEquals(1, result.size());
        assertEquals(booking1.getId(), result.get(0).getId());
    }

    @Test
    void testFindOverlappingBookingsJava_NoOverlap() {
        LocalDate startDate = LocalDate.of(2025, 1, 6);
        LocalDate endDate = LocalDate.of(2025, 1, 9);
        List<BookingEntity> bookings = Arrays.asList(booking1, booking2);

        List<BookingEntity> result = bookingService.findOverlappingBookingsJava(bookings, apartmentId1, startDate, endDate);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBookedApartmentIdsJava() {
        LocalDate startDate = LocalDate.of(2025, 1, 3);
        LocalDate endDate = LocalDate.of(2025, 1, 8);
        List<BookingEntity> bookings = Arrays.asList(booking1, booking2);

        List<String> result = bookingService.findBookedApartmentIdsJava(bookings, startDate, endDate);

        assertEquals(1, result.size());
        assertTrue(result.contains(apartmentId1));
        assertFalse(result.contains(apartmentId2));
    }

    @Test
    void testFindAvailableApartmentIds() {
        LocalDate startDate = LocalDate.of(2025, 1, 3);
        LocalDate endDate = LocalDate.of(2025, 1, 8);

        ApartmentEntity apartment1 = mock(ApartmentEntity.class);
        ApartmentEntity apartment2 = mock(ApartmentEntity.class);

        when(apartment1.getId()).thenReturn(apartmentId1);
        when(apartment2.getId()).thenReturn(apartmentId2);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1));
        when(apartmentRepository.findAll()).thenReturn(Arrays.asList(apartment1, apartment2));

        List<String> result = bookingService.findAvailableApartmentIds(startDate, endDate);

        assertEquals(1, result.size());
        assertTrue(result.contains(apartmentId2));
        assertFalse(result.contains(apartmentId1));
    }
}