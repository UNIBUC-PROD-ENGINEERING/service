package ro.unibuc.hello.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.data.entity.Actor;
import ro.unibuc.hello.data.entity.Movie;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface ActorRepository extends MongoRepository<Actor, String> {
    Optional<Actor> findByTmdbId(Long tmdbId);
}