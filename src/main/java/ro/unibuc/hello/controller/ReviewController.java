package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.dto.ReviewCreationRequestDto;
import ro.unibuc.hello.dto.ReviewUpdateRequestDto;
import ro.unibuc.hello.service.ReviewService;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    @ResponseBody
    public ResponseEntity<ReviewEntity> createReview(@RequestBody ReviewCreationRequestDto reviewDto) {
        ReviewEntity savedReview = reviewService.saveReview(reviewDto);
        return ResponseEntity.ok(savedReview);
    }

    @PatchMapping("/reviews/{id}")
    @ResponseBody
    public ResponseEntity<ReviewEntity> updateAuthor(@PathVariable String id,
            @RequestBody ReviewUpdateRequestDto updateReviewRequestDto) {
        var updatedReview = reviewService.updateReview(id, updateReviewRequestDto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable String id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully");
    }
}
