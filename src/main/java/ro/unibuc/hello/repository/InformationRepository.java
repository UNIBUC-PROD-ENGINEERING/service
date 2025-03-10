package ro.unibuc.hello.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.hello.model.InformationEntity;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface InformationRepository extends MongoRepository<InformationEntity, String> {

    InformationEntity findByTitle(String title);
    List<InformationEntity> findByDescription(String description);

}
