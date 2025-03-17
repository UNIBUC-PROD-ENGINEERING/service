package ro.unibuc.hello.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ro.unibuc.hello.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByMail(String mail);
    Optional<User> findByPhoneNumber(String phoneNumber);
    Optional<User> findById(String userId);
}
