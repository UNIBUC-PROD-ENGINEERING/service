package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ro.unibuc.hello.data.PartyEntity;

import java.util.List;

public interface PartyRepository extends MongoRepository<PartyEntity, String> {
    List<PartyEntity> findByName(String name);
    List<PartyEntity> findByUserIdsContaining(String userId);
}
