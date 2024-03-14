package ro.unibuc.contact.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    void deleteById(String id);

}
