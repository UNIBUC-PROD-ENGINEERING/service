package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.repository.ReviewRepository;
import ro.unibuc.hello.repository.BookingRepository;
import java.util.List;  
import java.util.Optional;  


@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
    }

    // Metodă pentru a obține toate review-urile
    public List<ReviewEntity> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Metodă pentru a obține un review după ID
    public Optional<ReviewEntity> getReviewById(String id) {
        return reviewRepository.findById(id);
    }

    // Metodă pentru a crea un review
    public ReviewEntity createReview(ReviewEntity review) {
        // Verificăm dacă rating-ul este valid
        if (review.getRating() == null) {
            throw new IllegalArgumentException("Rating is required");
        }
        
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        // Verificăm dacă utilizatorul a făcut un booking pentru apartamentul respectiv
        boolean hasBookedApartment = bookingRepository.findByApartmentIdAndUserId(review.getApartmentId(), review.getUserId()).size() > 0;
        if (!hasBookedApartment) {
            throw new IllegalArgumentException("User must have booked the apartment before leaving a review.");
        }

        // Dacă totul este valid, salvăm review-ul
        return reviewRepository.save(review);
    }

    // Metodă pentru a șterge un review
    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }
}
