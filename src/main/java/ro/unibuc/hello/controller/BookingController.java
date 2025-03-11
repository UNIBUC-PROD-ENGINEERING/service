package ro.unibuc.hello.controller;

import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.service.BookingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<BookingEntity> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Optional<BookingEntity> getBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }

@PostMapping
    public BookingEntity createBooking(@RequestBody BookingEntity booking) {
        try {
            return bookingService.createBooking(booking);
        } catch (IllegalArgumentException e) {
            // Afișează doar un mesaj simplu de eroare
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
    }
}
