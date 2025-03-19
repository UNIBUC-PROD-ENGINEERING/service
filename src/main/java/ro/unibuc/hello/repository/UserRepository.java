package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.UserEntity;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    List<UserEntity> findByUsernameContaining(String username);
    // UserEntity findById(String id);
    List<UserEntity> findByTier(int tier);
}
