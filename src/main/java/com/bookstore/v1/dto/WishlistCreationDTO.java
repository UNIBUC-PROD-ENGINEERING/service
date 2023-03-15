package com.bookstore.v1.dto;

import com.bookstore.v1.data.Review;
import com.bookstore.v1.data.Wishlist;

import java.util.List;

public class WishlistCreationDTO {
    private String id;
    private String title;
    private String userId;

    public WishlistCreationDTO() {
    }

    public WishlistCreationDTO(String title, String userId) {
        this.title = title;
        this.userId = userId;
    }

    public WishlistCreationDTO(String id, String title, String userId) {
        this.id = id;
        this.title = title;
        this.userId = userId;
    }

    public Wishlist toWishlist() {
        Wishlist wishlist = new Wishlist();
        wishlist.setId(id);
        wishlist.setTitle(title);
        return wishlist;
    }

    public Wishlist toWishlist(Boolean withoutId) {
        Wishlist wishlist = new Wishlist();
        if (!withoutId) {
            wishlist.setId(id);
        }
        wishlist.setTitle(title);
        return wishlist;
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
}
