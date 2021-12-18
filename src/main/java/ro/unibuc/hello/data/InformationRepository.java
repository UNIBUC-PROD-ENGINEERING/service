package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface InformationRepository extends MongoRepository<InformationEntity, String> {

    public InformationEntity findByTitle(String title);
    public List<InformationEntity> findByDescription(String description);

}