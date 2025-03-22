package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<ItemEntity, String> {
    List<ItemEntity> findByActive(boolean active);
    List<ItemEntity> findByNameContainingIgnoreCase(String name);
}