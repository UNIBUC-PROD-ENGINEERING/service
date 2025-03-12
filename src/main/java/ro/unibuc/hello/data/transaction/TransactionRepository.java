package ro.unibuc.hello.data.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<TransactionEntity, String> {
    List<TransactionEntity> findByUserId(String userId);
    List<TransactionEntity> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<TransactionEntity> findByLoyaltyCardId(String loyaltyCardId);
}