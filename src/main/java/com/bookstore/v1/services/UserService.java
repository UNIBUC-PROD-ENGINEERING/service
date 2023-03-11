package com.bookstore.v1.services;

import com.bookstore.v1.data.*;
import com.bookstore.v1.dto.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.bson.types.ObjectId;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    public List<UserDTO> getUsers() {
        List<UserDTO> userDTOs = new List<>();
        userRepository.findAll().forEach(user -> userDTOs.add(new UserDTO(user)));
        return userDTOs;
    }

    public UserDTO getUser(String id) {
        User user = userRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if(user != null)
            return new UserDTO(user);
        else
            return null;
    }

    public UserDTO insertUser(String userName, String email, String phoneNumber, List<String> reviewIds, List<String> wishlistIds) {
        User user = new User(userName, email, phoneNumber);
        List<Review> reviewEntities = new List<>();
        List<Wishlist> wishlistEntities = new List<>();

        if(!reviewIds.isEmpty() && reviewIds != null)
            reviewIds.forEach(id -> reviewEntities.add(reviewRepository.findById(String.valueOf(new ObjectId(id))).orElse(null)));
        else
            user.setReviews(null);

        if(!wishlistIds.isEmpty() && wishlistIds != null)
            wishlistIds.forEach(id -> wishlistEntities.add(wishlistRepository.findById(String.valueOf(new ObjectId(id))).orElse(null)));
        else
            user.setWishlist(null);

        if(!reviewEntities.isEmpty() && reviewEntities != null)
            user.setReviews(reviewEntities);

        if(!wishlistEntities.isEmpty() && wishlistEntities != null)
            user.setWishlists(wishlistEntities);

        return new UserDTO(userRepository.save(user));
    }

    public UserDTO updateUser(String id, String userName, String email, String phoneNumber, List<String> reviewIds, List<String> wishlistIds) {
        User user = userRepository.findById(String.valueOf(new ObjectId(id))).orElse(null);
        if(user != null) {
            if(userName != null)
                user.setUserName(userName);

            if(email != null)
                user.setEmail(email);

            if(phoneNumber != null)
                user.setPhoneNumber(phoneNumber);

            if(!reviewIds.isEmpty() && reviewIds != null) {
                List<Review> reviewEntities = new List<>();
                reviewIds.forEach(reviewId -> reviewEntities.add(reviewRepository.findById(String.valueOf(new ObjectId(reviewId))).orElse(null)));
                if(!reviewEntities.isEmpty())
                    user.setReviews(reviewEntities);
            }
            else
                user.setReviews(null);

            if(!wishlistIds.isEmpty() && wishlistIds != null) {
                List<Wishlist> wishlistEntities = new List<>();
                wishlistIds.forEach(wishlistId -> wishlistEntities.add(wishlistRepository.findById(String.valueOf(new ObjectId(wishlistId))).orElse(null)));
                if(!wishlistEntities.isEmpty())
                    user.setWishlists(wishlistEntities);
            }
            else
                user.setWishlists(null);

            return new UserDTO(userRepository.save(user));
        } else
            return  null;
    }

    public String deleteUser(String id) {
        userRepository.deleteById(String.valueOf(new ObjectId(id)));

        return "User having the id " + id + " was succesfully deleted!";
    }

    public UserDTO buggedInsertUser(String userName, String email, String phoneNumber, List<String> reviewIds, List<String> wishlistIds) {
        User user = new User(userName, email, phoneNumber);
        List<Review> reviewEntities = new List<>();
        List<Wishlist> wishlistEntities = new List<>();

        if(!reviewIds.isEmpty())
            reviewIds.forEach(id -> reviewEntities.add(reviewRepository.findById(String.valueOf(new ObjectId(id))).orElse(null)));
        else
            user.setReviews(null);

        if(!wishlistIds.isEmpty())
            wishlistIds.forEach(id -> wishlistEntities.add(wishlistRepository.findById(String.valueOf(new ObjectId(id))).orElse(null)));
        else
            user.setWishlists(null);

        if(!reviewEntities.isEmpty())
            user.setReviews(reviewEntities);

        if(!wishlistEntities.isEmpty())
            user.setWatchItems(wishlistEntities);

        return new UserDTO(userRepository.save(user));
    }
}
