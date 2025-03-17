package ro.unibuc.hello.data;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface ItemRepository extends MongoRepository<ItemEntity, String> {
    
    // @Aggregation(pipeline = {
    //     "{ '$lookup': { 'from': 'userEntity', 'localField': '_id', 'foreignField': 'items', 'as': 'owner' } }"
    // })
    // Optional<ItemEntity> findByIdWithReferences(String id);
}
