package ro.unibuc.hello.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.dto.ride.RideRequestDTO;
import ro.unibuc.hello.dto.ride.RideResponseDTO;
import ro.unibuc.hello.exceptions.ride.InvalidRideException;
import ro.unibuc.hello.exceptions.ride.RideConflictException;
import ro.unibuc.hello.model.Ride;
import ro.unibuc.hello.service.RideService;

@Controller
@RequestMapping("/rides")
public class RideController {
    
    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    // GET /rides 
    @GetMapping
    public ResponseEntity<List<RideResponseDTO>> getAllRides() {
        List<RideResponseDTO> rides = rideService.getAllRides()
                                              .stream()
                                              .map(RideResponseDTO::toDTO)
                                              .collect(Collectors.toList());
        return ResponseEntity.ok(rides);
    }

    // GET /rides/by-date?date=YYYY-MM-DD 
    @GetMapping("/by-date")
    public ResponseEntity<List<RideResponseDTO>> getRidesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<RideResponseDTO> rides = rideService.getRidesByDate(date)
                                                 .stream()
                                                 .map(RideResponseDTO::toDTO)
                                                 .collect(Collectors.toList());
        return ResponseEntity.ok(rides);
    }

    // POST /rides
    @PostMapping
    public ResponseEntity<?> createRide(@RequestBody RideRequestDTO rideRequestDTO) {
        try {
            RideResponseDTO rideResponse = rideService.createRide(rideRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (InvalidRideException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RideConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PatchMapping("/{rideId}/start")
    public ResponseEntity<RideResponseDTO> updateRideStatusToInProgress(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.updateRideStatusToInProgress(rideId));
    }

    // PATCH /rides/{rideId}/complete
    @PatchMapping("/{rideId}/complete")
    public ResponseEntity<RideResponseDTO> updateRideStatusToCompleted(
            @PathVariable String rideId,
            @RequestParam String currentLocation) {
        return ResponseEntity.ok(rideService.updateRideStatusToCompleted(rideId, currentLocation));
    }

    // PATCH /rides/{rideId}/cancel
    @PatchMapping("/{rideId}/cancel")
    public ResponseEntity<RideResponseDTO> updateRideStatusToCancelled(@PathVariable String rideId) {
        return ResponseEntity.ok(rideService.updateRideStatusToCancelled(rideId));
    }


}