package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import ro.unibuc.hello.models.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);  // Find user by email
}
