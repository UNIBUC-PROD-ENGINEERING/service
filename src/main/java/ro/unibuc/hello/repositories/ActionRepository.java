package ro.unibuc.hello.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entities.Action;

@Repository
public interface ActionRepository extends MongoRepository<Action, String>{

    Optional<Action> findById(String id);
    //List<ActionEntity> findByActionDescription(String description);

}
