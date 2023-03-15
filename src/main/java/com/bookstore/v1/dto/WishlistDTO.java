package com.bookstore.v1.dto;

import com.bookstore.v1.data.Review;
import com.bookstore.v1.data.Wishlist;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistDTO {
    private String id;
    private String title;
    private String userId;
    private UserDTO user;
    private List<BookDTO> books;

    public WishlistDTO() {
    }

    public WishlistDTO(String title) {
        this.title = title;
    }

    public WishlistDTO(String id, String title, String userId, UserDTO user, List<BookDTO> books) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.user = user;
        this.books = books;
    }

    public WishlistDTO(Wishlist wishlist, Boolean withUser) {
        this.id = wishlist.getId();
        this.title = wishlist.getTitle();
        this.userId = wishlist.getUser().getId();
        if (withUser) {
            this.user = new UserDTO(wishlist.getUser());
        }
        if(wishlist.getBooks() != null)
        this.books = wishlist.getBooks().stream().map(book->new BookDTO(book)).collect(Collectors.toList());
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

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
