package ro.unibuc.hello.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ro.unibuc.hello.data.SubscriptionEntity;


/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface SubscriptionRepository extends MongoRepository<SubscriptionEntity, String> {

    List<SubscriptionEntity> findByTier(int tier);
    List<SubscriptionEntity> findByTierLessThanEqual(int tier);
    List<SubscriptionEntity> findByPriceLessThanEqual(int price);
    List<SubscriptionEntity> findByTierAndPriceLessThanEqual(int tier, int price);

    boolean existsByTier(int tier);
}