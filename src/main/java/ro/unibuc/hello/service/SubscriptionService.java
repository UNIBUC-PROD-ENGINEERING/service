package ro.unibuc.hello.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.SubscriptionEntity;
import ro.unibuc.hello.data.SubscriptionRepository;
import ro.unibuc.hello.dto.Subscription;

import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.TierAlreadyExistsException;

import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getAllSubscriptions() {
        List<SubscriptionEntity> entities = subscriptionRepository.findAll();
        return convert(entities);
    }

    public List<Subscription> getSubscriptionsByTier(int tier) {
        List<SubscriptionEntity> entities = subscriptionRepository.findByTier(tier);
        return convert(entities);
    }

    public List<Subscription> getSubscriptionsUpToTier(int tier) {
        List<SubscriptionEntity> entities = subscriptionRepository.findByTierLessThanEqual(tier);
        return convert(entities);
    }

    public List<Subscription> getSubscriptionById(String id) {
        Optional<SubscriptionEntity> entity = subscriptionRepository.findById(id);
        
        List<Subscription> response = new ArrayList<>();
        
        if (!entity.isPresent()) {
            response.add(new Subscription());
        } else {
            SubscriptionEntity subscription = entity.get();
            response.add(new Subscription(subscription.getTier(), subscription.getPrice()));
        }
        
        return response;
    }

    public List<Subscription> getSubscriptionsByMaxPrice(int price) {
        List<SubscriptionEntity> entities = subscriptionRepository.findByPriceLessThanEqual(price);
        return convert(entities);
    }

    public List<Subscription> getSubscriptionsByTierAndMaxPrice(int tier, int price) {
        List<SubscriptionEntity> entities = subscriptionRepository.findByTierAndPriceLessThanEqual(tier, price);
        return convert(entities);
    }

    // public Subscription saveSubscription(Subscription subscription) {
    //     SubscriptionEntity entity = new SubscriptionEntity();
    //     entity.setTier(subscription.getTier());
    //     entity.setPrice(subscription.getPrice());
    //     subscriptionRepository.save(entity);
    //     return new Subscription(entity.getTier(), entity.getPrice());
    // }

    public Subscription saveSubscription(Subscription subscription) throws TierAlreadyExistsException {
        // Check if tier already exists
        if (subscriptionRepository.existsByTier(subscription.getTier())) {
            throw new TierAlreadyExistsException("Tier " + subscription.getTier() + " already exists. Cannot add duplicate tiers.");
        }
        
        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setTier(subscription.getTier());
        entity.setPrice(subscription.getPrice());
        subscriptionRepository.save(entity);
        return new Subscription(entity.getTier(), entity.getPrice());
    }

    public boolean deleteSubscription(String id) {
        Optional<SubscriptionEntity> entity = subscriptionRepository.findById(id);
        
        if (entity.isPresent()) {
            subscriptionRepository.delete(entity.get());
            return true;
        }
        
        return false;
    }

    private List<Subscription> convert(List<SubscriptionEntity> entities) {
        return entities.stream()
            .map(entity -> new Subscription(entity.getTier(), entity.getPrice()))
            .collect(Collectors.toList());
    }
}




// @Component
// public class SubscriptionsService {
//     @Autowired
//     private SubscriptionRepository subscriptionRepository;

//     public List<Subscription> getAllSubscriptions() {
//         List<SubscriptionEntity> entities = subscriptionRepository.findAll();
//         return entities.stream()
//             .map(entity -> new Subscription(entity.getTier(), entity.getPrice()))
//             .collect(Collectors.toList());
//     }

//     public List<Subscription> getSubscriptionsByTier(int tier) {
//         return subscriptionRepository.findByTier(tier).stream()
//             .map(this::convertToDto)
//             .collect(Collectors.toList());
//     }

//     public Subscription createSubscription(SubscriptionEntity subscriptionEntity) {
//         subscriptionRepository.save(subscriptionEntity);
//         return new Subscription(subscriptionEntity.getTier(), subscriptionEntity.getPrice());
//     }

//     public Subscription updateSubscription(String id, Subscription subscription) throws EntityNotFoundException {
//         SubscriptionEntity entity = subscriptionRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("Subscription not found with id: " + id));

//         entity.setTier(subscription.getTier());
//         entity.setPrice(subscription.getPrice());

//         SubscriptionEntity updatedEntity = subscriptionRepository.save(entity);
//         return convertToDto(updatedEntity);
//     }

//     public void deleteSubscription(String id) throws EntityNotFoundException {
//         if (!subscriptionRepository.existsById(id)) {
//             throw new EntityNotFoundException("Subscription not found with id: " + id);
//         }
//         subscriptionRepository.deleteById(id);
//     }

//     private Subscription convertToDto(SubscriptionEntity entity) {
//         return new Subscription(entity.getTier(), entity.getPrice());
//     }
// }
