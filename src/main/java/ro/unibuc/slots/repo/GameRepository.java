package ro.unibuc.slots.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.slots.model.Game;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    Optional<Game> findById(final String id);
    List<Game> findAll();
}
