package com.bookstore.v1.models;

import javax.persistence.*;

import java.util.List;

@Entity
@Table
public class Wishlist {
    @Id
    @SequenceGenerator(
            name="wishlist_sequence",
            sequenceName = "wishlist_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "wishlist_sequence"
    )
    Long id;
    String title;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "wishlist_book",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "wishlist_id", referencedColumnName = "id"))
    private List<Book> books;

    public Wishlist(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Wishlist(String title) {
        this.title = title;
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
}
