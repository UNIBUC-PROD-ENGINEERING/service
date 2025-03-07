package ro.unibuc.hello.controller;

import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.service.ReviewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewEntity> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public Optional<ReviewEntity> getReviewById(@PathVariable String id) {
        return reviewService.getReviewById(id);
    }

    @PostMapping
    public ReviewEntity createReview(@RequestBody ReviewEntity review) {
        return reviewService.createReview(review);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
    }
}
