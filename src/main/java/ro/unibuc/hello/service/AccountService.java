package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.GameRepository;
import ro.unibuc.hello.data.SubscriptionEntity;
import ro.unibuc.hello.data.SubscriptionRepository;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.Game;
import ro.unibuc.hello.dto.User;
import ro.unibuc.hello.dto.Subscription;
import ro.unibuc.hello.dto.Account;
import ro.unibuc.hello.dto.Notification;

import ro.unibuc.hello.service.GamesService;
import ro.unibuc.hello.exception.EntityNotFoundException;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class AccountService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GamesService gamesService;

    private List<Notification> createNotification(String message) {
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification(
            String.valueOf(System.currentTimeMillis()), // Simple ID generation
            message,
            new Date()
        ));
        return notifications;
    }

    public Account getAccount(String username, String password){
        List<UserEntity> entities = userRepository.findByUsernameContaining(username);

        if(entities.isEmpty()){
            return new Account("Invalid username!");
        }

        UserEntity entity = entities.get(0);

        if(!entity.getPassword().equals(password)){
            return new Account("Invalid password!");
        }

        User user = new User(entity.getId(), entity.getUsername(), entity.getPassword(), entity.getTier(), entity.getExpirationDate());

        Date expirationDate = user.getExpirationDate();
        Date todayDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Get notifications from the UserEntity
        List<Notification> notifications = entity.getNotifications();

        if(expirationDate == null){
            return new Account("Not yet purchased a subscription!", user, null, notifications);
        }

        if(todayDate.after(expirationDate)){
            return new Account("Subscription expired!", user, null, notifications);
        }

        List<Game> games = gamesService.getGamesAvailable(entity.getTier());

        return new Account("Logged in!", user, games, notifications);
    }

    public String getUpgrades(String username, String password){
        List<UserEntity> entities = userRepository.findByUsernameContaining(username);

        if(entities.isEmpty()){
            return "Invalid username!";
        }

        UserEntity entity = entities.get(0);

        if(!entity.getPassword().equals(password)){
            return "Invalid password!";
        }

        boolean isExpired;
        Date expirationDate = entity.getExpirationDate();
        if(expirationDate != null){
            Date todayDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            isExpired = !todayDate.before(expirationDate);
        }else{
            isExpired = true;
        }

        List<SubscriptionEntity> subscriptionsEntities = subscriptionRepository.findAll();

        int currentTier = !isExpired ? entity.getTier() : 0;

        int currentPrice = subscriptionsEntities.stream()
            .filter(subs -> subs.getTier() == currentTier)
            .mapToInt(SubscriptionEntity::getPrice)
            .findFirst()
            .orElse(0);

        double discountPercentage = 0.20; // 20% discount on upgrades

        List<String> strings = subscriptionsEntities.stream()
            .map(subs -> {
                int tier = subs.getTier();
                int originalPrice = subs.getPrice();
        
                // If tier is lower or equal to the current tier, show the original price (no discount)
                if (tier <= currentTier) {
                    return String.format("Tier %d for %d$ (Original Price)", tier, originalPrice);
                }
        
                // Otherwise, apply a discount to the upgrade price
                int originalUpgradeCost = originalPrice - currentPrice;
                int discountedPrice = (int) (originalUpgradeCost * (1 - discountPercentage)); // Apply 20% discount
        
                return String.format("Tier %d for %d$ (Original: %d$)", tier, discountedPrice, originalUpgradeCost);
            })
            .collect(Collectors.toList());

        return String.join("\n", strings);
    }

    public String upgradeTier(String username, String password, int targetTier) {
        List<UserEntity> entities = userRepository.findByUsernameContaining(username);
    
        if (entities.isEmpty()) {
            return "Invalid username!";
        }
    
        UserEntity entity = entities.get(0);
    
        if (!entity.getPassword().equals(password)) {
            return "Invalid password!";
        }
    
        boolean isExpired;
        Date expirationDate = entity.getExpirationDate();
        Date todayDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        if (expirationDate != null) {
            isExpired = !todayDate.before(expirationDate);
        } else {
            isExpired = true;
        }
    
        List<SubscriptionEntity> subscriptionsEntities = subscriptionRepository.findAll();
    
        int currentTier = !isExpired ? entity.getTier() : 0;
    
        int currentPrice = subscriptionsEntities.stream()
            .filter(subs -> subs.getTier() == currentTier)
            .mapToInt(SubscriptionEntity::getPrice)
            .findFirst()
            .orElse(0);
    
        int targetPrice = subscriptionsEntities.stream()
            .filter(subs -> subs.getTier() == targetTier)
            .mapToInt(SubscriptionEntity::getPrice)
            .findFirst()
            .orElse(-1);
    
        if (targetPrice == -1) {
            return "Tier not found!";
        }
    
        if (currentTier == targetTier) {
            return String.format("You already own tier %d!", targetTier);
        }
    
        int finalPrice;
    
        if (currentTier < targetTier) {
            // Apply discount only for upgrades
            double discountPercentage = 0.20; // 20% discount
            int upgradeCost = targetPrice - currentPrice;
            finalPrice = (int) (upgradeCost * (1 - discountPercentage));
        } else {
            // Full price for downgrades or re-purchases
            finalPrice = targetPrice;
        }
    
        // Extend subscription by 1 year
        Calendar cal = Calendar.getInstance();
        cal.setTime(todayDate);
        cal.add(Calendar.YEAR, 1);
    
        entity.setExpirationDate(cal.getTime());
        entity.setTier(targetTier);
        userRepository.save(entity);

        // Create and save notification
        List<Notification> notifications = entity.getNotifications();
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        notifications.add(new Notification(
            String.valueOf(System.currentTimeMillis()), // Simple ID generation
            "You have successfully upgraded to tier " + targetTier + "for $" +  finalPrice + "!",
            new Date()
        ));
        entity.setNotifications(notifications);

        userRepository.save(entity);
    
        return String.format("Purchased tier %d for %d$ (Original: %d$)", targetTier, finalPrice, targetPrice);
    }

    public String cancelSubscription(String username, String password){
        List<UserEntity> entities = userRepository.findByUsernameContaining(username);

        if(entities.isEmpty()){
            return "Invalid username!";
        }

        UserEntity entity = entities.get(0);

        if(!entity.getPassword().equals(password)){
            return "Invalid password!";
        }

        boolean isExpired;
        Date expirationDate = entity.getExpirationDate();
        Date todayDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        if(expirationDate != null){
            isExpired = !todayDate.before(expirationDate);
        }else{
            isExpired = true;
        }

        if(isExpired || entity.getTier() == 0){
            return "You do not have a subscription!";
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(expirationDate);
        cal.add(Calendar.MONTH, -1);

        entity.setExpirationDate(null);
        entity.setTier(0);
        userRepository.save(entity);

        List<Notification> notifications = entity.getNotifications();
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        notifications.add(new Notification(
            String.valueOf(System.currentTimeMillis()), // Simple ID generation
            "You have successfully canceled your subscription!",
            new Date()
        ));
        entity.setNotifications(notifications);

        userRepository.save(entity);

        if(todayDate.before(cal.getTime())){
            notifications.add(new Notification(
                String.valueOf(System.currentTimeMillis()), // Simple ID generation
                "You canceled your subscription and paid extra for early cancellation.",
                new Date()
            ));
            entity.setNotifications(notifications);

            userRepository.save(entity);

            return "You canceled your subscription and paid extra for early cancellation.";
        }

        notifications.add(new Notification(
            String.valueOf(System.currentTimeMillis()), // Simple ID generation
            "You have successfully canceled your subscription!",
            new Date()
        ));
        entity.setNotifications(notifications);

        userRepository.save(entity);

        return "You canceled your subscription."; 
    }
}