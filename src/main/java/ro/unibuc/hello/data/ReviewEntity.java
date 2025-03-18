package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "reviews")
public class ReviewEntity {
    
    @Id
    private String id;

    private String comment;

    private Integer rating; // Rating-ul review-ului (între 1 și 5)

    private String apartmentId;
    private String userId;

     private Set<String> likes = new HashSet<>();
    private Set<String> dislikes = new HashSet<>();


    // Constructori, getteri și setteri
    public ReviewEntity() {}

    public ReviewEntity(String comment, Integer rating, String apartmentId, String userId) {
        this.comment = comment;
        this.rating = rating;
        this.apartmentId = apartmentId;
        this.userId = userId;
    }
        public ReviewEntity(String id, String comment, Integer rating, String apartmentId, String userId) {
        this.comment = comment;
        this.rating = rating;
        this.apartmentId = apartmentId;
        this.userId = userId;
        this.id=id;
    }
    

    // Getteri și setteri
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(String apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getters și Setters pentru like-uri și dislike-uri
    public void setLikes(Set<String> likes) {
    this.likes = likes;
}

public void setDislikes(Set<String> dislikes) {
    this.dislikes = dislikes;
}
    
    public Set<String> getLikes() {
        return likes;
    }

    public void addLike(String userId) {
        this.likes.add(userId);
    }

    public void removeLike(String userId) {
        this.likes.remove(userId);
    }

    public Set<String> getDislikes() {
        return dislikes;
    }

    public void addDislike(String userId) {
        this.dislikes.add(userId);
    }

    public void removeDislike(String userId) {
        this.dislikes.remove(userId);
    }

}
