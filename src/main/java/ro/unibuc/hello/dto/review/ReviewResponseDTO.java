package ro.unibuc.hello.dto.review;

import ro.unibuc.hello.model.Review;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ReviewResponseDTO {
    private String reviewerId;
    private String reviewedId;
    private String rideId;
    private int rating;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
    private Instant createdAt;

    public ReviewResponseDTO() {}

    public ReviewResponseDTO(String reviewerId, String reviewedId, String rideId, 
                            int rating, String comment, 
                            Instant createdAt) {
        this.reviewerId = reviewerId;
        this.reviewedId = reviewedId;
        this.rideId = rideId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public static ReviewResponseDTO toDTO(Review review) {
        return new ReviewResponseDTO(
            review.getReviewerId(),
            review.getReviewedId(),
            review.getRideId(),
            review.getRating(),
            review.getComment(),    
            review.getCreatedAt()
        );
    } 
}
