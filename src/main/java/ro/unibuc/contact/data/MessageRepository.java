package ro.unibuc.contact.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface MessageRepository extends MongoRepository<MessageEntity, String> {

    List<MessageEntity> findByUserId(String userId);

}