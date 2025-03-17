package ro.unibuc.hello.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.service.*;
import ro.unibuc.hello.dto.rideBooking.RideBookingRequestDTO;
import ro.unibuc.hello.dto.rideBooking.RideBookingResponseDTO;
import ro.unibuc.hello.service.RideBookingService;
import ro.unibuc.hello.exceptions.ride.InvalidRideException;
import ro.unibuc.hello.exceptions.rideBooking.InvalidRideBookingException;
import ro.unibuc.hello.exceptions.rideBooking.RideBookingConflictException;
import ro.unibuc.hello.model.User;
import ro.unibuc.hello.model.*;

@Controller
@RequestMapping("/bookings")
public class RideBookingController {
    
    private final RideBookingService rideBookingService;

    public RideBookingController(RideBookingService rideBookingService)
    {
        this.rideBookingService = rideBookingService;
    }

    //Get all passengers for a ride: /bookings/{rideId}/passengers

    @GetMapping("/{rideId}/passengers")
    public ResponseEntity<List<RideBookingResponseDTO>> getPassengersByRideId(@PathVariable String rideId) {
        List<RideBookingResponseDTO> passengers = rideBookingService.getPassengersByRideId(rideId);
        return ResponseEntity.ok(passengers);
    }

    @PostMapping
    public ResponseEntity<?> createRideBooking(@RequestBody RideBookingRequestDTO rideBookingRequestDTO)
    {
        try {
            RideBookingResponseDTO rideBookingResponse = rideBookingService.createRideBooking(rideBookingRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (InvalidRideBookingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RideBookingConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating ride: " + e.getMessage());
        }
    }

    @PatchMapping("/{rideId}/cancel")
    public ResponseEntity<?> updateRideBookingStatusToCancelled(@PathVariable String rideId)
    {
        try {
            rideBookingService.updateRideBookingStatusToCancelled(rideId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        } catch (InvalidRideBookingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error cancelling ride: " + e.getMessage());
        }
    }
}
