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
import ro.unibuc.hello.enums.RideBookingStatus;
import ro.unibuc.hello.model.Review;
import ro.unibuc.hello.model.RideBooking;
import ro.unibuc.hello.model.Ride;
import ro.unibuc.hello.repository.ReviewRepository;

import ro.unibuc.hello.exceptions.review.InvalidReviewException;

import ro.unibuc.hello.repository.RideRepository;
import ro.unibuc.hello.repository.UserRepository;
import ro.unibuc.hello.repository.RideBookingRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final RideBookingRepository rideBookingRepository;

    public ReviewService(ReviewRepository reviewRepository, 
                        RideRepository rideRepository, 
                        UserRepository userRepository,
                        RideBookingRepository rideBookingRepository
                        ) {
        this.reviewRepository = reviewRepository;
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.rideBookingRepository = rideBookingRepository;
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


        List<RideBooking> rideBookingList = rideBookingRepository.findByRideIdAndPassengerId(
            reviewRequestDTO.getRideId(), reviewRequestDTO.getReviewerId());

        // Check if reviewer is passenger
        if (rideBookingList.isEmpty()) {
            throw new InvalidReviewException("Reviewer is not a passenger.");
        }

        RideBooking rideBooking = rideBookingList.get(0);

        // Check if reviewer cancelled the ride
        if (rideBooking.getRideBookingStatus().equals(RideBookingStatus.CANCELLED)) {
            throw new InvalidReviewException("Reviewer cancelled ride.");
        }

        Optional<Review> existingReview = reviewRepository.findByRideIdAndReviewerId(
            reviewRequestDTO.getRideId(), reviewRequestDTO.getReviewerId());

        // Check if reviewer already reviewed ride
        if (existingReview.isPresent()) {
            throw new InvalidReviewException("Reviewer already made a review for this ride");
        }

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
