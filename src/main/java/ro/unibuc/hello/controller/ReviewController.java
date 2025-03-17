package ro.unibuc.hello.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ro.unibuc.hello.dto.review.ReviewRequestDTO;
import ro.unibuc.hello.dto.review.ReviewResponseDTO;
import ro.unibuc.hello.exceptions.review.InvalidReviewException;
import ro.unibuc.hello.model.Review;
import ro.unibuc.hello.service.ReviewService;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // GET /reviews/by-ride/{id}
    @GetMapping("/by-ride/{id}")
    public ResponseEntity<List<Review>> getReviewsByRide(
        @PathVariable(name="id", required=true) String id) {
        List<Review> reviews = reviewService.getReviewsByRide(id);
        return ResponseEntity.ok(reviews);
    }

    // GET /reviews/by-driver/{id}
    @GetMapping("/by-driver/{id}")
    public ResponseEntity<List<Review>> getReviewsByDriver(
        @PathVariable(name="id", required=true) String id) {
            List<Review> reviews = reviewService.getReviewsByDriver(id);
            return ResponseEntity.ok(reviews);
    }

    // POST /reviews
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        try {
            ReviewResponseDTO reviewResponse = reviewService.createReview(reviewRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (InvalidReviewException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating review: " + e.getMessage());
        }
    }
}