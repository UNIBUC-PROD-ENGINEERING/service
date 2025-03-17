package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.StringOperators.SubstrCP;
import org.springframework.stereotype.Controller;

import ro.unibuc.hello.data.SubscriptionEntity;
// import ro.unibuc.hello.dto.Game;
import ro.unibuc.hello.dto.Subscription;
import ro.unibuc.hello.exception.EntityNotFoundException;
// import ro.unibuc.hello.service.GamesService;
import ro.unibuc.hello.service.SubscriptionsService;
import ro.unibuc.hello.service.GreetingsService;

import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {
    
    @Autowired
    private SubscriptionsService subscriptionsService;

    @GetMapping // Inherits "/subscriptions" from the class
    @ResponseBody
    public List<Subscription> getAllSubscriptions() {
        return subscriptionsService.getAllSubscriptions();
    }

    // Create a new subscription
    @PostMapping // Inherits "/subscriptions" from the class
    @ResponseBody
    public Subscription createSubscription(@RequestBody Subscription subscription) {
        SubscriptionEntity entity = new SubscriptionEntity(subscription.getTier(), subscription.getPrice());
        return subscriptionsService.createSubscription(entity);
    }

}
