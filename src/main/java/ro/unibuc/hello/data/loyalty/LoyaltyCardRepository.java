package ro.unibuc.hello.data.loyalty;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoyaltyCardRepository extends MongoRepository<LoyaltyCardEntity, String> {
    List<LoyaltyCardEntity> findByUserId(String userId);
    List<LoyaltyCardEntity> findByCardType(LoyaltyCardEntity.CardType cardType);
}