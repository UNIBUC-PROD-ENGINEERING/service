package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayerRepository extends MongoRepository<PlayerEntity, String> {
    PlayerEntity findByName(String name);
    PlayerEntity findById(Integer id);
    void deleteById(Integer id);
}
