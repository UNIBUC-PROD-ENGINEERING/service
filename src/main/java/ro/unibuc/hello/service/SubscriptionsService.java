package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// import ro.unibuc.hello.data.GameEntity;
// import ro.unibuc.hello.data.GameRepository;
// import ro.unibuc.hello.dto.Game;
import ro.unibuc.hello.data.SubscriptionEntity;
import ro.unibuc.hello.data.SubscriptionRepository;
import ro.unibuc.hello.dto.Subscription;

import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class SubscriptionsService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getAllSubscriptions() {
        List<SubscriptionEntity> entities = subscriptionRepository.findAll();
        return entities.stream()
            .map(entity -> new Subscription(entity.getTier(), entity.getPrice()))
            .collect(Collectors.toList());
    }

    public Subscription createSubscription(SubscriptionEntity subscriptionEntity) {
        subscriptionRepository.save(subscriptionEntity);
        return new Subscription(subscriptionEntity.getTier(), subscriptionEntity.getPrice());
    }

}
