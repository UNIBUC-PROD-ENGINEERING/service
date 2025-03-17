package ro.unibuc.hello.model;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Document("reviews")
@NoArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    private String id;
    private String reviewerId;
    private String reviewedId;
    private String rideId;
    private int rating;
    private String comment;
    private Instant createdAt;

    public Review(String reviewerId, String reviewedId, String rideId, int rating, String comment) {
        this.reviewerId = reviewerId;
        this.reviewedId = reviewedId;
        this.rideId = rideId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public String getReviewedId() {
        return reviewedId;
    }

    public String getRideId() {
        return rideId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public void setReviewedId(String reviewedId) {
        this.reviewedId = reviewedId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", reviewerId='" + reviewerId + '\'' +
                ", reviewedId='" + reviewedId + '\'' +
                ", rideId='" + rideId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
