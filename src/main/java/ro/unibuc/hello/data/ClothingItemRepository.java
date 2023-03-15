package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothingItemRepository  extends MongoRepository<ClothingItemEntity, String> {
    ClothingItemEntity findByTitle(String title);
    List<ClothingItemEntity> findByDescription(String description);
}
