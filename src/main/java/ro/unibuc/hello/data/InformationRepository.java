package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface InformationRepository extends MongoRepository<InformationEntity, String> {

    InformationEntity findByTitle(String title);
    List<InformationEntity> findByDescription(String description);

}
