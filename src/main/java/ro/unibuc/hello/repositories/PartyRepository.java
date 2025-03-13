package ro.unibuc.hello.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.unibuc.hello.models.Party;
import java.util.List;

public interface PartyRepository extends MongoRepository<Party, String> {
    List<Party> findByName(String name);
    List<Party> findByUserIdsContaining(String userId);
}
