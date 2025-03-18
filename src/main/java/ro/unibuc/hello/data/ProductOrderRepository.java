package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends MongoRepository<ProductOrderEntity, String> {
}