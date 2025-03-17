package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
