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

        if(expirationDate == null){
            return new Account("Not yet purchased a subscription!", user, null);
        }

        if(todayDate.after(expirationDate)){
            return new Account("Subscription expired!", user, null);
        }

        List<Game> games = gamesService.getGamesAvailable(entity.getTier());

        return new Account("Logged in!", user, games);
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
        
        List<String> strings = subscriptionsEntities.stream()
            .filter(subs -> subs.getTier() > currentTier)
            .map(subs -> String.format("Tier %d for %d$", subs.getTier(), subs.getPrice() - currentPrice))
            .collect(Collectors.toList());

        return String.join("\n", strings);
    }

    public String upgradeTier(String username, String password, int targetTier){
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

        if(targetPrice == -1){
            return "Tier not found!";
        }

        if(currentTier == targetTier)
            return String.format("You already own tier %d!", targetTier);

        if(currentTier > targetTier)
            return "Cannot upgrade to a lower tier!";

        Calendar cal = Calendar.getInstance();
        cal.setTime(todayDate);
        cal.add(Calendar.YEAR, 1);

        entity.setExpirationDate(cal.getTime());
        entity.setTier(targetTier);
        userRepository.save(entity);

        return String.format("Upgraded to tier %d for %d$", targetTier, targetPrice - currentPrice);
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

        if(todayDate.before(cal.getTime())){
            return "You canceled your subscription and paid extra for early cancellation.";
        }

        return "You canceled your subscription."; 
    }

}