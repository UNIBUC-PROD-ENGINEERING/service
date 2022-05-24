package ro.unibuc.slots.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.slots.model.InformationEntity;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface InformationRepository extends MongoRepository<InformationEntity, String> {
    InformationEntity findByTitle(String title);
}
