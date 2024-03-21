package ro.unibuc.hello.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.unibuc.hello.data.ReadingRecordEntity;
import ro.unibuc.hello.data.ReadingRecordRepository;
import ro.unibuc.hello.data.ReviewEntity;
import ro.unibuc.hello.data.ReviewRepository;
import ro.unibuc.hello.dto.ReadingRecordCreationRequestDto;
import ro.unibuc.hello.dto.ReviewCreationRequestDto;
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

}
