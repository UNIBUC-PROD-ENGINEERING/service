package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.CategoryEntity;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {
    Optional<CategoryEntity> findByNameEquals(String categoryName);
}
