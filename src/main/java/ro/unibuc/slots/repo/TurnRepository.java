package ro.unibuc.slots.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.slots.model.Turn;

@Repository
public interface TurnRepository extends MongoRepository<Turn, String> {}
