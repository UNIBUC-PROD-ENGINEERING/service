package ro.unibuc.hello.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findByUsername(String username);

    @Aggregation(pipeline = {
            "{ '$lookup': { 'from': 'itemEntity', 'localField': '_id', 'foreignField': 'owner', 'as': 'items' } }"
    })
    List<UserEntity> findAllWithItems();


    @Aggregation(pipeline = {
        "{ '$match': { '_id': ?0 } }", // Match the user by ID
        "{ '$lookup': { 'from': 'itemEntity', 'localField': '_id', 'foreignField': 'owner', 'as': 'items' } }"
    })
    Optional<UserEntity> findByIdWithItems(String id);
}