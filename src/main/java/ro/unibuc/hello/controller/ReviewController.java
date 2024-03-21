package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.dto.ReviewCreationRequestDto;
import ro.unibuc.hello.service.ReviewService;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewEntity> createReview(@RequestBody ReviewCreationRequestDto reviewDto) {
        ReviewEntity savedReview = reviewService.saveReview(reviewDto);
        return ResponseEntity.ok(savedReview);
    }
}