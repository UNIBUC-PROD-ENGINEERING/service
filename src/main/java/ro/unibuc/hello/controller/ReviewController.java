package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.service.ReviewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // @GetMapping
    // public List<ReviewEntity> getAllReviews() {
    //     return reviewService.getAllReviews();
    // }

    @GetMapping("/{id}")
    public Optional<ReviewEntity> getReviewById(@PathVariable String id) {
        return reviewService.getReviewById(id);
    }

    @PostMapping
    public String createReview(@RequestBody ReviewEntity review) {
        try {
            reviewService.createReview(review);
            return "Review successfully created!";
        } catch (IllegalArgumentException e) {
            return e.getMessage(); // Returnează mesajul de eroare, cum ar fi "User must have booked the apartment before leaving a review."
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
    }

     @GetMapping
    public List<ReviewEntity> getAllReviewsSortedByRating() {
        return reviewService.getAllReviewsSortedByRating(); 
    }

    // Obține review-urile bune (rating > 3)
    @GetMapping("/good")
    public List<ReviewEntity> getGoodReviews() {
        return reviewService.getGoodReviews();
    }

    // Obține review-urile rele (rating <= 3)
    @GetMapping("/bad")
    public List<ReviewEntity> getBadReviews() {
        return reviewService.getBadReviews();
    }

    // Adaugă un like la review
    @PostMapping("/{reviewId}/like")
    public String addLike(@PathVariable String reviewId, @RequestParam String userId) {
        return reviewService.addLike(reviewId, userId);
    }

    // Adaugă un dislike la review
    @PostMapping("/{reviewId}/dislike")
    public String addDislike(@PathVariable String reviewId, @RequestParam String userId) {
        return reviewService.addDislike(reviewId, userId);
    }

    @PostMapping("/{reviewId}/removeReaction")
    public String removeReaction(@PathVariable String reviewId, @RequestParam String userId) {
        return reviewService.removeReaction(reviewId, userId);
    }
}
