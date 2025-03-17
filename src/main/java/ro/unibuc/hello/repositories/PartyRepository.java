package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ro.unibuc.hello.data.PartyEntity;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends MongoRepository<PartyEntity, String> {
    List<PartyEntity> findByName(String name);
    Optional<PartyEntity> findById(String id);
    List<PartyEntity> findByUserIdsContaining(String userId);
}
