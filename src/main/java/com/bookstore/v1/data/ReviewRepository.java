package com.bookstore.v1.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<Review> findByUserAndBook(User user, Book book);

    List<Review> findAllByUser(User user);

    List<Review> findAllByBook(Book book);
}
