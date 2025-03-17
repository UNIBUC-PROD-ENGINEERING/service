package ro.unibuc.hello.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ro.unibuc.hello.dto.review.ReviewRequestDTO;
import ro.unibuc.hello.dto.review.ReviewResponseDTO;
import ro.unibuc.hello.enums.RideStatus;
import ro.unibuc.hello.model.Review;
import ro.unibuc.hello.model.Ride;
import ro.unibuc.hello.repository.ReviewRepository;

import ro.unibuc.hello.exceptions.review.InvalidReviewException;

import ro.unibuc.hello.repository.RideRepository;
import ro.unibuc.hello.repository.UserRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, 
                        RideRepository rideRepository, 
                        UserRepository userRepository
                        ) {
        this.reviewRepository = reviewRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    public List<Review> getReviewsByRide(String id) {
        return reviewRepository.findByRideId(id);
    }

    public List<Review> getReviewsByDriver(String id) {
        return reviewRepository.findByReviewedId(id);
    }

    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        Optional<Ride> rideOptional = rideRepository.findById(reviewRequestDTO.getRideId());

        // Check if ride exists in rides table
        if (rideOptional.isEmpty()) {
            throw new InvalidReviewException("Ride does not exist.");
        } 

        Ride ride = rideOptional.get();
    
        // Check if ride status is completed
        if (!ride.getStatus().equals(RideStatus.COMPLETED)) {
            throw new InvalidReviewException("Ride is not completed.");
        }

        // TODO The booking status (for ride_id and reviewer_id) must not be CANCELLED.

        Optional<Review> existingReview = reviewRepository.findByRideIdAndReviewerId(
        reviewRequestDTO.getRideId(), reviewRequestDTO.getReviewerId());

        // Check if reviewer already reviewed ride
        if (existingReview.isPresent()) {
            throw new InvalidReviewException("Reviewer already made a review for this ride");
        }

        // TODO reviewer_id must be listed as a passenger_id in the ride with the given ride_id

        // TODO The reviewer_id’s booking status for that ride must be BOOKED

        // Check if reviewer exists in users table
        if (!userRepository.existsById(reviewRequestDTO.getReviewerId())) {
            throw new InvalidReviewException("Reviewer does not exist as user.");
        }

        // Check if reviewed is driver of ride
        if (!ride.getDriverId().equals(reviewRequestDTO.getReviewedId())) {
            throw new InvalidReviewException("Reviewed is not driver of ride");
        }

        // Check if reviewed exists in users table
        if (!userRepository.existsById(reviewRequestDTO.getReviewedId())) {
            throw new InvalidReviewException("Reviewed does not exist as user.");
        }

        // Check if reviewer is different from reviewed
        if (reviewRequestDTO.getReviewerId().equals(reviewRequestDTO.getReviewedId())) {
            throw new InvalidReviewException("Reviewer can't also be reviewed.");
        }

        Review newReview = reviewRequestDTO.toEntity();

        reviewRepository.save(newReview);

        return ReviewResponseDTO.toDTO(newReview);

    }
}
