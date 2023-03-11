package com.bookstore.v1.data;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("reviews")
public class Review {
    @Id
    private String id;
    private String title;
    private String description;
    private Double rating;

    @DBRef(lazy = true)
    User user;
    @DBRef(lazy = true)
    Book book;

    public Review() {}

    public Review(String id, String title, String description, Double rating) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public Review(String title, String description, Double rating) {
        this.title = title;
        this.description = description;
        this.rating = rating;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Review{" +
               "id='" + id + '\'' +
               ", title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", rating=" + rating +
               ", user=" + user +
               ", book=" + book +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id) && Objects.equals(title, review.title) &&
               Objects.equals(description, review.description) &&
               Objects.equals(rating, review.rating) && Objects.equals(user, review.user) &&
               Objects.equals(book, review.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, rating, user, book);
    }
}
