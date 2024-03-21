package ro.unibuc.hello.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.unibuc.hello.data.ReadingRecordRepository;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.data.ReviewRepository;
import ro.unibuc.hello.dto.ReviewCreationRequestDto;
import ro.unibuc.hello.dto.ReviewUpdateRequestDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReadingRecordRepository readingRecordRepository;

    public ReviewEntity saveReview(ReviewCreationRequestDto reviewCreationRequestDto) {
        log.debug("Attempting to save a review with readingRecordId: {}",
                reviewCreationRequestDto.getReadingRecordId());

        var readingRecordEntity = readingRecordRepository.findById(reviewCreationRequestDto.getReadingRecordId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "ReadingRecordEntity not found for id: " + reviewCreationRequestDto.getReadingRecordId()));

        var reviewEntity = ReviewEntity.builder()
                .rating(reviewCreationRequestDto.getRating())
                .reviewBody(reviewCreationRequestDto.getReviewBody())
                .datePosted(LocalDate.now())
                .readingRecord(readingRecordEntity)
                .build();

        log.debug("Review saved successfully for readingRecordId: {}", reviewCreationRequestDto.getReadingRecordId());
        return reviewRepository.save(reviewEntity);
    }

    public ReviewEntity updateReview(String id, ReviewUpdateRequestDto reviewUpdateRequestDto) {
        log.debug("Updating the review with id '{}', setting rating '{}' and text '{}'", id,
                reviewUpdateRequestDto.getRating(), reviewUpdateRequestDto.getReviewBody());
        var review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        review.setRating(reviewUpdateRequestDto.getRating());
        review.setReviewBody(reviewUpdateRequestDto.getReviewBody());
        return reviewRepository.save(review);
    }

    public void deleteReview(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
