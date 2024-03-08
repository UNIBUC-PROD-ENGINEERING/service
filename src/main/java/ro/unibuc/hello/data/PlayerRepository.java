package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<PlayerEntity,String> {
    PlayerEntity findByName(String name);

}
