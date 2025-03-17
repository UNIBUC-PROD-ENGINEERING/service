package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.hello.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByRideId(String rideId);
    List<Review> findByReviewedId(String reviewdId);
    Optional<Review> findByRideIdAndReviewerId(String rideId, String reviewerId);
}
