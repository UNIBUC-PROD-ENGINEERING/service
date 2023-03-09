package com.bookstore.v1.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document("users")
public class User {
    @Id
    private String id;
    private String userName;
    private String email;
    private String phoneNumber;

    @DBRef(lazy = true)
    private List<Review> reviews;
    @DBRef(lazy = true)
    private List<Wishlist> wishlists;

    public User() {}

    public User(String userName, String email, String phoneNumber) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User(String id, String userName, String email, String phoneNumber) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    @Override
    public String toString() {
        return "User{" +
               "id='" + id + '\'' +
               ", userName='" + userName + '\'' +
               ", email='" + email + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", reviews=" + reviews +
               ", wishlists=" + wishlists +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) &&
               Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) &&
               Objects.equals(reviews, user.reviews) && Objects.equals(wishlists, user.wishlists);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email, phoneNumber, reviews, wishlists);
    }
}
