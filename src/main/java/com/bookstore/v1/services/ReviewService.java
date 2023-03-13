package com.bookstore.v1.services;

import com.bookstore.v1.data.*;
import com.bookstore.v1.dto.ReviewCreationDTO;
import com.bookstore.v1.dto.ReviewDTO;
import com.bookstore.v1.exception.DuplicateObjectException;
import com.bookstore.v1.exception.EmptyFieldException;
import com.bookstore.v1.exception.EntityNotFoundException;
import com.bookstore.v1.exception.InvalidDoubleRange;
import com.bookstore.v1.validations.ReviewValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    public ReviewDTO addReview(ReviewCreationDTO reviewCreationDTO) throws EmptyFieldException, InvalidDoubleRange,
            EntityNotFoundException {
        // validate review creation dto without the id since it will be auto-generated
        ReviewValidations.validateReviewCreationDTO(reviewCreationDTO, false);

        // convert to review entity and get relations
        Review review = reviewCreationDTO.toReview(true);

        Optional<User> user = userRepository.findById(reviewCreationDTO.getUserId());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("user");
        }
        Optional<Book> book = bookRepository.findById(reviewCreationDTO.getBookId());
        if (book.isEmpty()) {
            throw new EntityNotFoundException("book");
        }
        Optional<Review> existingReview = reviewRepository.findByUserAndBook(user.get(), book.get());
        if (existingReview.isPresent()) {
            throw new DuplicateObjectException("review");
        }

        review.setUser(user.get());
        review.setBook(book.get());
        reviewRepository.save(review);

        return new ReviewDTO(review, true, true);
    }

    public ReviewDTO updateReview(ReviewCreationDTO reviewUpdateDTO) throws EmptyFieldException, InvalidDoubleRange,
            EntityNotFoundException {
        // validate review update dto with the id since the object should already exist
        ReviewValidations.validateReviewCreationDTO(reviewUpdateDTO, true);

        Optional<Review> oldReviewOpt = reviewRepository.findById(reviewUpdateDTO.getId());
        if (oldReviewOpt.isEmpty()) {
            throw new EntityNotFoundException("review");
        }

        Review newReview = oldReviewOpt.get();
        newReview.setTitle(reviewUpdateDTO.getTitle());
        newReview.setDescription(reviewUpdateDTO.getDescription());
        newReview.setRating(reviewUpdateDTO.getRating());
        reviewRepository.save(newReview);

        return new ReviewDTO(newReview, true, true);
    }

    public void deleteReviewById(String reviewId) throws EntityNotFoundException {
        Optional<Review> reviewToDelete = reviewRepository.findById(reviewId);
        if (reviewToDelete.isEmpty()) {
            throw new EntityNotFoundException("review");
        }
        reviewRepository.delete(reviewToDelete.get());
    }

    public ReviewDTO getReviewById(String reviewId) throws EntityNotFoundException {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isEmpty()) {
            throw new EntityNotFoundException("review");
        }
        return new ReviewDTO(review.get(), true, true);
    }

    public List<ReviewDTO> getReviews() {
        return reviewRepository
                .findAll()
                .stream()
                .map(review -> new ReviewDTO(review, true, true))
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getBookReviews(String bookId) throws EntityNotFoundException {
        Optional<Book> bookFilter = bookRepository.findById(bookId);
        if (bookFilter.isEmpty()) {
            throw new EntityNotFoundException("book");
        }
        return reviewRepository
                .findAllByBook(bookFilter.get())
                .stream()
                .map(review -> new ReviewDTO(review, true, false))
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getUserReviews(String userId) throws EntityNotFoundException {
        Optional<User> userFilter = userRepository.findById(userId);
        if (userFilter.isEmpty()) {
            throw new EntityNotFoundException("user");
        }
        return reviewRepository
                .findAllByUser(userFilter.get())
                .stream()
                .map(review -> new ReviewDTO(review, false, true))
                .collect(Collectors.toList());
    }
}
