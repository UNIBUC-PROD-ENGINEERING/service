package ro.unibuc.hello.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.BookingEntity;
import ro.unibuc.hello.service.BookingService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<?> createBooking(@RequestBody BookingEntity booking) {
        try {
            BookingEntity createdBooking = bookingService.createBooking(booking);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
    }
    
    @GetMapping("/by-apartment/{apartmentId}")
    public List<BookingEntity> getBookingsForApartment(@PathVariable String apartmentId) {
        return bookingService.getBookingsForApartment(apartmentId);
    }
    
    @GetMapping("/by-apartment-and-user")
    public List<BookingEntity> getBookingsForApartmentAndUser(
            @RequestParam String apartmentId, 
            @RequestParam String userId) {
        return bookingService.getBookingsForApartmentAndUser(apartmentId, userId);
    }
    
    @GetMapping("/check-availability/{apartmentId}")
    public ResponseEntity<?> checkAvailability(
            @PathVariable String apartmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        try {
            boolean isAvailable = bookingService.isApartmentAvailable(apartmentId, startDate, endDate);
            
            Map<String, Object> response = new HashMap<>();
            response.put("apartmentId", apartmentId);
            response.put("startDate", startDate);
            response.put("endDate", endDate);
            response.put("available", isAvailable);
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
