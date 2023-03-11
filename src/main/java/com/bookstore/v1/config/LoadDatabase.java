package com.bookstore.v1.config;

import com.bookstore.v1.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;

@Configuration
public class LoadDatabase {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private WishlistRepository wishlistRepository;

    @Bean
    CommandLineRunner initDb() {
        // create a document for each model and save it to the database
        // otherwise mongo won't create the collections until they are being first referenced
        Book book = new Book("title", "author", "publisher", "isbn", LocalDate.EPOCH);
        book = bookRepository.insert(book);
        User user = new User("username", "email", "phoneNumber");
        user = userRepository.insert(user);
        Review review = new Review("title", "description", 5.0);
        review.setUser(user);
        review.setBook(book);
        review = reviewRepository.insert(review);
        Wishlist wishlist = new Wishlist("title");
        wishlist.setUser(user);
        ArrayList<Book> wishlistBooks = new ArrayList<>();
        wishlistBooks.add(book);
        wishlist.setBooks(wishlistBooks);
        wishlist = wishlistRepository.insert(wishlist);

        // we can delete the documents used to create the collections
        wishlistRepository.delete(wishlist);
        reviewRepository.delete(review);
        bookRepository.delete(book);
        userRepository.delete(user);

        return null;
    }
}
