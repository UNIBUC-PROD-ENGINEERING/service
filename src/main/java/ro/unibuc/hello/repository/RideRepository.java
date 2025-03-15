package ro.unibuc.hello.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ro.unibuc.hello.model.Ride;

public interface RideRepository extends MongoRepository<Ride, String> {

    @Query("{ 'driverId': ?0, " +
           "'departureTime': { : ?2 }, " +
           "'arrivalTime': { : ?1 } }")
    Optional<Ride> findByDriverIdAndTimeOverlap(String driverId, Instant departureTime, Instant arrivalTime);

    @Query("{ 'departureTime': { $gte: ?0, $lt: ?1 } }")
    List<Ride> findAllByDepartureDate(Instant startOfDay, Instant endOfDay);
}
