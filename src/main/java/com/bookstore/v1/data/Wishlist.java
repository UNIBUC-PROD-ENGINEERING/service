package com.bookstore.v1.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document("wishlists")
public class Wishlist {
    @Id
    private String id;
    private String title;

    @DBRef(lazy = true)
    User user;
    @DBRef(lazy = true)
    private List<Book> books = new ArrayList<>();

    public Wishlist() {}

    public Wishlist(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Wishlist(String title) {
        this.title = title;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    @Override
    public String toString() {
        return "Wishlist{" +
               "id='" + id + '\'' +
               ", title='" + title + '\'' +
               ", user=" + user +
               ", books=" + books +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(id, wishlist.id) && Objects.equals(title, wishlist.title) &&
               Objects.equals(user, wishlist.user) && Objects.equals(books, wishlist.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, user, books);
    }
}
