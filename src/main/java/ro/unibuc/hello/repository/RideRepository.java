package ro.unibuc.hello.repository;

import java.time.Instant;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ro.unibuc.hello.model.Ride;

public interface RideRepository extends MongoRepository<Ride, String> {

    @Query("{ 'driverId': ?0, " +
       "'departureTime': { $lt: ?2 }, " + // Departure time is before the new ride's arrival time
       "'arrivalTime': { $gt: ?1 } }")   // Arrival time is after the new ride's departure time
    Optional<Ride> findByDriverIdAndTimeOverlap(String driverId, Instant departureTime, Instant arrivalTime);

    @Query("{ 'departureTime': { $gte: ?0, $lt: ?1 } }") // Corrected field name to 'departureTime'
    List<Ride> findAllByDepartureDate(Instant startOfDay, Instant endOfDay);
}
