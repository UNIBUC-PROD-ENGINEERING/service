package ro.unibuc.hello.data.product;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    ProductEntity findByName(String name);
}
