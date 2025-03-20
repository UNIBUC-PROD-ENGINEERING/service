package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.StringOperators.SubstrCP;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;

import ro.unibuc.hello.data.SubscriptionEntity;
import ro.unibuc.hello.dto.Subscription;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.TierAlreadyExistsException;

import ro.unibuc.hello.service.SubscriptionService;
import ro.unibuc.hello.service.GreetingsService;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/subscriptions")
    @ResponseBody
    public List<Subscription> getSubscriptions(
        @RequestParam("tier") Optional<Integer> tier,
        @RequestParam("id") Optional<String> id,
        @RequestParam("price") Optional<Integer> price
    ) {
        if (id.isPresent()) {
            return subscriptionService.getSubscriptionById(id.get());
        }

        if (tier.isPresent() && price.isPresent()) {
            return subscriptionService.getSubscriptionsByTierAndMaxPrice(tier.get(), price.get());
        }

        if (tier.isPresent()) {
            return subscriptionService.getSubscriptionsByTier(tier.get());
        }

        if (price.isPresent()) {
            return subscriptionService.getSubscriptionsByMaxPrice(price.get());
        }

        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/subscriptions/uptotier")
    @ResponseBody
    public List<Subscription> getSubscriptionsUpToTier(
        @RequestParam(name="tier", required=true) int tier
    ) {
        return subscriptionService.getSubscriptionsUpToTier(tier);
    }

    // @PostMapping("/add-subscription")
    // @ResponseBody
    // public Subscription saveSubscription(
    //     @RequestParam(name="tier", required=true) int tier,
    //     @RequestParam(name="price", required=true) int price
    // ) {
    //     Subscription subscription = new Subscription(tier, price);
    //     return subscriptionService.saveSubscription(subscription);
    // }

    // SubscriptionController.java
    @PostMapping("/add-subscription")
    @ResponseBody
    public ResponseEntity<?> saveSubscription(
        @RequestParam(name="tier", required=true) int tier,
        @RequestParam(name="price", required=true) int price
    ) {
        try {
            Subscription subscription = new Subscription(tier, price);
            return ResponseEntity.ok(subscriptionService.saveSubscription(subscription));
        } catch (TierAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/delete-subscription")
    @ResponseBody
    public String deleteSubscription(
        @RequestParam(name="id", required=true) String id
    ) {
        if (subscriptionService.deleteSubscription(id)) {
            return "Success.";
        }
        
        return "No subscription with this id.";
    }
}




// @Controller
// @RequestMapping("/subscriptions")
// public class SubscriptionController {
    
//     @Autowired
//     private SubscriptionsService subscriptionsService;

//     // @GetMapping
//     // @ResponseBody
//     // public List<Subscription> getAllSubscriptions() {
//     //     return subscriptionsService.getAllSubscriptions();
//     // }

//     @GetMapping
//     @ResponseBody
//     public List<Subscription> getSubscriptions(
//         @RequestParam("id") Optional<String> id,
//         @RequestParam("tier") Optional<Integer> tier
//     ) {
//         if (id.isPresent()) {
//             return List.of(subscriptionsService.getSubscriptionById(id.get()));
//         }

//         if (tier.isPresent()) {
//             return subscriptionsService.getSubscriptionsByTier(tier.get());
//         }

//         return subscriptionsService.getAllSubscriptions();
//     }

//     // Create a new subscription
//     @PostMapping
//     @ResponseBody
//     public Subscription createSubscription(@RequestBody Subscription subscription) {
//         SubscriptionEntity entity = new SubscriptionEntity(subscription.getTier(), subscription.getPrice());
//         return subscriptionsService.createSubscription(entity);
//     }

//     /// todo: list by tier

//     /// todo: update

//     /// todo: delete

//     @DeleteMapping("/{id}")
//     @ResponseBody
//     public void deleteSubscription(@PathVariable String id) throws EntityNotFoundException {
//         subscriptionsService.deleteSubscription(id);
//     }
// }
