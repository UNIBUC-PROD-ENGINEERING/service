package ro.unibuc.hello.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends MongoRepository<ActionEntity, String>{

    Optional<ActionEntity> findById(String id);
    //List<ActionEntity> findByActionDescription(String description);

}
