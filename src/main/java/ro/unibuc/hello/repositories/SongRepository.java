package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

import ro.unibuc.hello.data.SongEntity;

import java.util.List;

public interface SongRepository extends MongoRepository<SongEntity, String> {
    List<SongEntity> findByTitle(String title); // Example of a valid query
}
