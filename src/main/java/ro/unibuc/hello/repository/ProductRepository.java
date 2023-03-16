package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    @Override
    Optional<ProductEntity> findById(String id);
    List<ProductEntity> findByProductNameContainsIgnoreCase(String name);
    List<ProductEntity> findByPriceBetween(Float lower, Float upper);
    void deleteById(String id);
}


