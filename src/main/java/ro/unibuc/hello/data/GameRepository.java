package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GameRepository extends MongoRepository<GameEntity, String> {
    
}
