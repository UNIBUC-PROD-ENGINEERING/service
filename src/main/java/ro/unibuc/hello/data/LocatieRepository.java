package ro.unibuc.hello.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.hello.data.LocatieEntity;

@Repository
public interface LocatieRepository extends MongoRepository<LocatieEntity, String> {
    Optional<LocatieEntity> findById(String id);
}
