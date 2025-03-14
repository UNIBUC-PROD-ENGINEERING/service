package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.repository.ReviewRepository;
import ro.unibuc.hello.repository.BookingRepository;
import org.springframework.data.domain.Sort; 
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

    public List<ReviewEntity> getAllReviewsSortedByRating() {
        return reviewRepository.findAll(Sort.by(Sort.Order.desc("rating"))); // Sortare descrescătoare după rating
    }

    // Metoda pentru a obține review-urile bune (rating > 3)
    public List<ReviewEntity> getGoodReviews() {
        return reviewRepository.findByRatingGreaterThan(3, Sort.by(Sort.Order.desc("rating"))); // Review-uri cu rating > 3
    }

    // Metoda pentru a obține review-urile rele (rating <= 3)
    public List<ReviewEntity> getBadReviews() {
        return reviewRepository.findByRatingLessThanEqual(3, Sort.by(Sort.Order.asc("rating"))); // Review-uri cu rating <= 3
    }

// Adaugă un like la un review
    public String addLike(String reviewId, String userId) {
        Optional<ReviewEntity> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            ReviewEntity r = review.get();

            // Dacă utilizatorul a dat deja un dislike, îl ștergem
            if (r.getDislikes().contains(userId)) {
                r.removeDislike(userId);
            }

            // Dacă utilizatorul a dat deja un like, nu facem nimic
            if (r.getLikes().contains(userId)) {
                return "User has already liked this review.";
            }

            // Adăugăm like
            r.addLike(userId);
            reviewRepository.save(r);
            return "Like added successfully!";
        }
        return "Review not found!";
    }

    // Adaugă un dislike la un review
    public String addDislike(String reviewId, String userId) {
        Optional<ReviewEntity> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            ReviewEntity r = review.get();

            // Dacă utilizatorul a dat deja un like, îl ștergem
            if (r.getLikes().contains(userId)) {
                r.removeLike(userId);
            }

            // Dacă utilizatorul a dat deja un dislike, nu facem nimic
            if (r.getDislikes().contains(userId)) {
                return "User has already disliked this review.";
            }

            // Adăugăm dislike
            r.addDislike(userId);
            reviewRepository.save(r);
            return "Dislike added successfully!";
        }
        return "Review not found!";
    }

    // Șterge reacția utilizatorului la un review (like sau dislike)
    public String removeReaction(String reviewId, String userId) {
        Optional<ReviewEntity> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            ReviewEntity r = review.get();

            // Dacă utilizatorul a dat like
            if (r.getLikes().contains(userId)) {
                r.removeLike(userId);
                reviewRepository.save(r);
                return "Like removed successfully!";
            }

            // Dacă utilizatorul a dat dislike
            if (r.getDislikes().contains(userId)) {
                r.removeDislike(userId);
                reviewRepository.save(r);
                return "Dislike removed successfully!";
            }

            return "No reaction found to remove.";
        }
        return "Review not found!";
    }

}

