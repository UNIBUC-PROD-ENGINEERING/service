package com.bookstore.v1.controllers;

import com.bookstore.v1.dto.ReviewCreationDTO;
import com.bookstore.v1.dto.ReviewDTO;
import com.bookstore.v1.exception.DuplicateObjectException;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import com.bookstore.v1.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add-review")
    @ResponseBody
    public ReviewDTO addReview(@RequestBody ReviewCreationDTO reviewCreationDTO) throws EmptyFieldException,
            InvalidDoubleRange, EntityNotFoundException, DuplicateObjectException {
        return reviewService.addReview(reviewCreationDTO);
    }

    @PutMapping("/update-review")
    @ResponseBody
    public ReviewDTO updateReview(@RequestBody ReviewCreationDTO reviewUpdateDTO) throws EmptyFieldException,
            InvalidDoubleRange, EntityNotFoundException {
        return reviewService.updateReview(reviewUpdateDTO);
    }

    @DeleteMapping("/delete-review/{reviewId}")
    @ResponseBody
    public void deleteReview(@PathVariable String reviewId) throws EntityNotFoundException {
        reviewService.deleteReviewById(reviewId);
    }

    @GetMapping("/get-review/{reviewId}")
    @ResponseBody
    public ReviewDTO getReviewById(@PathVariable String reviewId) throws EntityNotFoundException {
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping("/get-reviews")
    @ResponseBody
    public List<ReviewDTO> getReviews() {
        return reviewService.getReviews();
    }

    @GetMapping("/get-book-reviews/{bookId}")
    @ResponseBody
    public List<ReviewDTO> getBookReviews(@PathVariable String bookId) throws EntityNotFoundException {
        return reviewService.getBookReviews(bookId);
    }

    @GetMapping("/get-user-reviews/{userId}")
    @ResponseBody
    public List<ReviewDTO> getUserReviews(@PathVariable String userId) throws EntityNotFoundException {
        return reviewService.getUserReviews(userId);
    }
}
