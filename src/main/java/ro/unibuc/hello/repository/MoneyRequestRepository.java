package ro.unibuc.hello.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.MoneyRequest;

import java.util.List;

@Repository
public interface MoneyRequestRepository extends MongoRepository<MoneyRequest, String> {
    List<MoneyRequest> findByToAccountId(String toAccountId); // Get all requests for a user
    List<MoneyRequest> findByFromAccountId(String fromAccountId); // Get requests a user made
}
