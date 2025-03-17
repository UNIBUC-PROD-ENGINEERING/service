package ro.unibuc.hello.repository;

import java.time.Instant;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ro.unibuc.hello.model.RideBooking;
import ro.unibuc.hello.model.Ride;

public interface RideBookingRepository extends MongoRepository<RideBooking, String> {

    // Query bookings by passengerId
    List<RideBooking> findByPassengerId(String passengerId);

    // Query bookings created within a specific time range
    List<RideBooking> findByCreatedAtBetween(Instant startTime, Instant endTime);

    List<RideBooking>findByRideIdAndPassengerId(String rideId, String passengerId);

    List<RideBooking> findByRideId(String rideId);
    
    
    //overlapping rides for passenger
   @Query("{ 'passengerId': ?0, 'departureTime': { $lt: ?2 }, 'arrivalTime': { $gt: ?1 } }")
    List<RideBooking> findOverlappingRidesForPassenger(String passengerId, Instant departureTime, Instant arrivalTime);


    //overlapping rides for driver
    @Query("{ 'driverId': ?0, 'departureTime': { $lt: ?2 }, 'arrivalTime': { $gt: ?1 } }")
    List<RideBooking> findOverlappingRidesForDriver(String driverId, Instant departureTime, Instant arrivalTime);

}