package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class ReviewEntity {
    @Id
    private String id;
    private String comment;
    private Integer rating;
    private String apartmentId;
    private String userId;

    public ReviewEntity() {}

    public ReviewEntity(String comment, Integer rating, String apartmentId, String userId) {
        this.comment = comment;
        this.rating = rating;
        this.apartmentId = apartmentId;
        this.userId = userId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getApartmentId() { return apartmentId; }
    public void setApartmentId(String apartmentId) { this.apartmentId = apartmentId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
