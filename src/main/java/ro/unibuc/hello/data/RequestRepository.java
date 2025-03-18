package ro.unibuc.hello.data;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface RequestRepository extends MongoRepository<RequestEntity, String> {

    Optional<RequestEntity> findByUsernameAndToDoList(String username, String toDoList);
    
}
