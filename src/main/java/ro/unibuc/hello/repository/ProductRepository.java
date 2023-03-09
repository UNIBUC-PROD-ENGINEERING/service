package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.ProductEntity;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {
}
