package ro.unibuc.hello.data.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.unibuc.hello.data.product.ProductEntity;

public interface TransactionRepository extends MongoRepository<TransactionEntity, String> {
}