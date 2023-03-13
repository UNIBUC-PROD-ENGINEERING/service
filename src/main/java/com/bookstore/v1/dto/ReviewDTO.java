package com.bookstore.v1.dto;

import com.bookstore.v1.data.Review;

public class ReviewDTO {
    private String id;
    private String title;
    private String description;
    private Double rating;
    private String userId;
    private UserDTO user;
    private String bookId;
    private BookDTO book;

    public ReviewDTO() {}

    public ReviewDTO(String id, String title, String description, Double rating, String userId, String bookId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.userId = userId;
        this.bookId = bookId;
    }

    public ReviewDTO(Review review, Boolean withUser, Boolean withBook) {
        this.id = review.getId();
        this.title = review.getTitle();
        this.description = review.getDescription();
        this.rating = review.getRating();
        this.userId = review.getUser().getId();
        this.bookId = review.getBook().getId();
        if (withUser) {
            this.user = new UserDTO(review.getUser());
        }
        if (withBook) {
            this.book = new BookDTO(review.getBook());
        }
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }
}
