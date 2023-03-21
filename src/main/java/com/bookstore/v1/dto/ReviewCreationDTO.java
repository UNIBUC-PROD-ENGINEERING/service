package com.bookstore.v1.dto;

import com.bookstore.v1.data.Review;

import java.util.Objects;

public class ReviewCreationDTO {
    private String id;
    private String title;
    private String description;
    private Double rating;
    private String userId;
    private String bookId;

    public ReviewCreationDTO() {}

    public ReviewCreationDTO(String title, String description, Double rating, String userId, String bookId) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.userId = userId;
        this.bookId = bookId;
    }

    public ReviewCreationDTO(String id, String title, String description, Double rating, String userId, String bookId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.userId = userId;
        this.bookId = bookId;
    }

    public Review toReview() {
        Review review = new Review();
        review.setId(id);
        review.setTitle(title);
        review.setDescription(description);
        review.setRating(rating);
        return review;
    }

    public Review toReview(Boolean withoutId) {
        Review review = new Review();
        if (!withoutId) {
            review.setId(id);
        }
        review.setTitle(title);
        review.setDescription(description);
        review.setRating(rating);
        return review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewCreationDTO that = (ReviewCreationDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) &&
               Objects.equals(description, that.description) && Objects.equals(rating, that.rating) &&
               Objects.equals(userId, that.userId) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, rating, userId, bookId);
    }
}
