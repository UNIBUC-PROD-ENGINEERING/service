package com.bookstore.v1.models;

import javax.persistence.*;

@Entity
@Table
public class Review {

    @Id
    @SequenceGenerator(
            name="review_sequence",
            sequenceName = "review_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "review_sequence"
    )
    Long id;
    String title;
    String description;
    Double rating;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    Book book;
    public Review(Long id, String title, String description, Double rating) {
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
