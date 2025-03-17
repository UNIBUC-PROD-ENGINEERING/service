package ro.unibuc.hello.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ro.unibuc.hello.dto.rideBooking.RideBookingRequestDTO;
import ro.unibuc.hello.dto.rideBooking.RideBookingResponseDTO;
import ro.unibuc.hello.model.RideBooking;
import ro.unibuc.hello.dto.ride.RideRequestDTO;
import ro.unibuc.hello.dto.ride.RideResponseDTO;
import ro.unibuc.hello.enums.RideStatus;
import ro.unibuc.hello.exceptions.rideBooking.InvalidRideBookingException;
import ro.unibuc.hello.exceptions.ride.InvalidRideException;
import ro.unibuc.hello.model.Ride;
import ro.unibuc.hello.model.User;
import ro.unibuc.hello.repository.UserRepository;
import ro.unibuc.hello.repository.RideRepository;
import ro.unibuc.hello.service.UserService;
import ro.unibuc.hello.repository.RideBookingRepository;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.enums.RideBookingStatus;



@Service
public class RideBookingService {
    private final RideBookingRepository rideBookingRepository;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;
    private final UserService userService;

    public RideBookingService(RideBookingRepository rideBookingRepository, UserRepository userRepository, RideRepository rideRepository, UserService userService)
    {
        this.rideBookingRepository = rideBookingRepository;
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
        this.userService = userService;
    }

    public List<RideBookingResponseDTO> getPassengersByRideId(String rideId) {

        List<RideBooking> bookings = rideBookingRepository.findByRideId(rideId);
        
        return bookings.stream()
            .map(booking -> {
                // Get passenger information
                User passenger = userRepository.findById(booking.getPassengerId())
                .orElseThrow(() -> new EntityNotFoundException("User"));
                
                // Create response DTO
                RideBookingResponseDTO responseDTO = RideBookingResponseDTO.toDTO(booking);
                
                // Set passenger full name
                responseDTO.setPassengerFullName(passenger.getFirstName() + " " + passenger.getLastName());
                
                return responseDTO;
            })
            .collect(Collectors.toList());
    }

    public RideBookingResponseDTO createRideBooking (RideBookingRequestDTO rideBookingRequestDTO)
    {
        //check if passenger id is in users collection
        if(!userRepository.existsById(rideBookingRequestDTO.getPassengerId())){
            throw new InvalidRideBookingException("Passenger's id doesnt exist");
        }

        //ride id has to exist
        Ride ride = rideRepository.findById(rideBookingRequestDTO.getRideId())
            .orElseThrow(() -> new InvalidRideException("Ride ID does not exist."));

        //passenger shouldnt have already booked
        List<RideBooking> existingBookings = rideBookingRepository.findByRideIdAndPassengerId(
            ride.getId(), rideBookingRequestDTO.getPassengerId());
        if (!existingBookings.isEmpty()) {
            throw new InvalidRideBookingException("Passenger already booked for this ride.");
        }
            

        //check if the passenger has a conflicting ride
        boolean hasPassengerConflict = !rideBookingRepository.findOverlappingRidesForPassenger(
            rideBookingRequestDTO.getPassengerId(),
            ride.getDepartureTime(),
            ride.getArrivalTime()
        ).isEmpty();

        if (hasPassengerConflict) {
            throw new InvalidRideBookingException("Passenger has a conflicting ride.");
        }

        //check if the driver has a conflicting ride
        boolean hasDriverConflict = !rideBookingRepository.findOverlappingRidesForDriver(
            ride.getDriverId(),
            ride.getDepartureTime(),
            ride.getArrivalTime()).isEmpty();

        if (hasDriverConflict) {
            throw new InvalidRideBookingException("Driver has another ride scheduled at the same time.");
        }

        //available seats >0
        if(ride.getSeatsAvailable() < 1) {
            throw new InvalidRideBookingException("No more seats available");
        }

        //ride has to be scheduled

        if(ride.getStatus() != RideStatus.SCHEDULED)
        {
            throw new InvalidRideBookingException("Ride is not scheduled");
        }

       RideBooking newRideBooking = rideBookingRequestDTO.toEntity();

       rideBookingRepository.save(newRideBooking);

       ride.setSeatsAvailable(ride.getSeatsAvailable()-1);
       rideRepository.save(ride);

       return RideBookingResponseDTO.toDTO(newRideBooking);
    }

    public RideBookingResponseDTO updateRideBookingStatusToCancelled(String rideId) {
        RideBooking rideBooking = rideBookingRepository.findById(rideId)
                .orElseThrow(() -> new InvalidRideBookingException("RideBooking not found."));
        
        Ride ride = rideRepository.findById(rideBooking.getRideId())
                .orElseThrow(() -> new InvalidRideException("Ride not found."));
        
        // check if the rideBooking status is BOOKED
        if (rideBooking.getRideBookingStatus() != RideBookingStatus.BOOKED) {
            throw new InvalidRideBookingException("Ride status must be BOOKED to cancel.");
        }
    
        // Check if instant.now < departure time
        if (Instant.now().isBefore(ride.getDepartureTime())) {
            throw new InvalidRideBookingException("Ride cannot be cancelled after it started.");
        }
    
        rideBooking.setRideBookingStatus(RideBookingStatus.CANCELLED);
        
        return RideBookingResponseDTO.toDTO(rideBookingRepository.save(rideBooking));
    }
    
    

}
