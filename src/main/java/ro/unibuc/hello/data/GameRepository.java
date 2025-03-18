package ro.unibuc.hello.data;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    // Spring Data JPA provides basic CRUD operations
    // You can add custom query methods here if needed
}