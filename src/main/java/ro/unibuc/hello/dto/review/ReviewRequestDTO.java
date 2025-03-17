package ro.unibuc.hello.dto.review;

import ro.unibuc.hello.model.Review;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

public class ReviewRequestDTO {
    private String reviewerId;
    private String reviewedId;
    private String rideId;
    private int rating;
    private String comment;

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewedId() {
        return reviewedId;
    }

    public void setReviewedId(String reviewedId) {
        this.reviewedId = reviewedId;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Review toEntity() {
        return new Review(
            this.reviewerId,
            this.reviewedId,
            this.rideId,
            this.rating,
            this.comment
        );
    }
}
